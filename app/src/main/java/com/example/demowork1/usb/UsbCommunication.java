package com.example.demowork1.usb;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author hangang.
 * _@date: Created on 2022/8/29.
 * _@description:
 */
public class UsbCommunication implements BaseCommunication, UsbDetachedReceiver.UsbDetachedListener, OpenDevicesReceiver.OpenDevicesListener {
    //usb设备连接广播
    private OpenDevicesReceiver mOpenDevicesReceiver;
    //usb连接断开广播
    private UsbDetachedReceiver mUsbDetachedReceiver;
    //需要使用context初始化相关usb资源
    private Context mContext;
    //usb设备管理器
    private UsbManager mUsbManager;
    //自定义usb权限action
    private static final String USB_ACTION = "com.wiseasy.communication.usb.hostchart";

    /**
     * Handler相关判断的变量，对应：usb连接初始化成功、失败，usb连接接收消息成功、失败，usb连接发送消息成功、失败
     */
    private static final int RECEIVER_MESSAGE_SUCCESS = 1;
    private static final int SEND_MESSAGE_SUCCESS = 2;
    private static final int RECEIVER_MESSAGE_FAILED = 3;
    private static final int SEND_MESSAGE_FAILED = 4;
    private static final int INIT_FAILED = 5;
    private static final int INIT_SUCCESS = 6;

    //接收消息监听回调
    private ReceiverMessageListener receiverMessageListener;
    //接收消息监听回调
    private SendMessageListener sendMessageListener;


    /**
     * 使用handler将所有的操作统一到主线程
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVER_MESSAGE_SUCCESS://成功接受到数据
                    receiverMessageListener.onSuccess((byte[]) msg.obj);
                    break;
                case RECEIVER_MESSAGE_FAILED://接收消息失败
                    receiverMessageListener.onFailed("接受消息失败");
                    break;
                case SEND_MESSAGE_SUCCESS://发送数据成功
                    sendMessageListener.onSuccess();
                    break;
                case SEND_MESSAGE_FAILED://发送数据失败
                    sendMessageListener.onFailed("发送消息失败");
                    break;
                case INIT_SUCCESS://usb主附设备连接成功
                    communicationListener.onSuccess(CommunicationCode.USB_OPEN_SUCCESS, "初始化成功");
                    break;
                case INIT_FAILED://usb主附设备连接失败
                    communicationListener.onFailed("初始化失败");
                    break;
            }
        }
    };

    /**
     * 单例模式 初始化
     *
     * @param context
     */
    private UsbCommunication(Context context) {
        /**
         * 为了避免在单利模式下的内存泄露，这里将context统一转换为ApplicationContext
         */
        this.mContext = context.getApplicationContext();

        //注册usb连接断开的广播
        mUsbDetachedReceiver = new UsbDetachedReceiver(this);
        IntentFilter intentFilter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED);
        mContext.registerReceiver(mUsbDetachedReceiver, intentFilter);

        //通过context获取到当前系统的USB管理器
        mUsbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
    }

    //单例变量
    private static volatile UsbCommunication Instance;

    /**
     * 单利模式双重检查
     *
     * @param context
     * @return
     */
    public static UsbCommunication getInstance(Context context) {
        if (Instance == null) {
            synchronized (UsbCommunication.class) {
                if (Instance == null) {
                    Instance = new UsbCommunication(context);
                }
            }
        }
        return Instance;
    }

    //usb连接开启成功或失败的回调
    private CommunicationListener communicationListener;

    /**
     * 开启usb连接的接口实现
     *
     * @param communicationListener
     */
    @Override
    public void openCommunication(CommunicationListener communicationListener) {
        this.communicationListener = communicationListener;
        //用来申请usb权限
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(USB_ACTION), 0);
        //注册usb连接开启的广播
        mOpenDevicesReceiver = new OpenDevicesReceiver(this);
        IntentFilter intentFilter = new IntentFilter(USB_ACTION);
        mContext.registerReceiver(mOpenDevicesReceiver, intentFilter);
        //设置连接成功的监听回调
        mOpenDevicesReceiver.setCommunicationListener(communicationListener);
        /**
         * 通过usbManager获取当前连接的设备集合
         * 遍历集合通过productId过滤不符合条件的设备
         */
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        if (deviceList != null) {
            for (UsbDevice usbDevice : deviceList.values()) {
                int productId = usbDevice.getProductId();
                if (productId != 377 && productId != 7205) {
                    if (mUsbManager.hasPermission(usbDevice)) {
                        /**
                         * 通过AOA协议让附件设备进入到accessory模式
                         */
                        initAccessory(usbDevice);
                    } else {
                        //申请usb权限
                        mUsbManager.requestPermission(usbDevice, pendingIntent);
                    }
                }
            }
        } else {
            //连接失败回调
            communicationListener.onFailed("请连接USB");
        }

    }

    /**
     * AOA协议部分
     *
     * @param usbDevice
     */
    private void initAccessory(UsbDevice usbDevice) {
        //获取usb连接通道
        UsbDeviceConnection usbDeviceConnection = mUsbManager.openDevice(usbDevice);
        if (usbDeviceConnection == null) {
            communicationListener.onFailed("请连接USB");
            return;
        }
        //根据AOA协议打开Accessory模式
        initStringControlTransfer(usbDeviceConnection, 0, "Google, Inc."); // MANUFACTURER
        initStringControlTransfer(usbDeviceConnection, 1, "AccessoryChat"); // MODEL
        initStringControlTransfer(usbDeviceConnection, 2, "Accessory Chat"); // DESCRIPTION
        initStringControlTransfer(usbDeviceConnection, 3, "1.0"); // VERSION
        initStringControlTransfer(usbDeviceConnection, 4, "http://www.android.com"); // URI
        initStringControlTransfer(usbDeviceConnection, 5, "0123456789"); // SERIAL
        usbDeviceConnection.controlTransfer(0x40, 53, 0, 0, new byte[]{}, 0, 100);
        usbDeviceConnection.close();
        initDevice();//子设备初始化
    }

    private void initStringControlTransfer(UsbDeviceConnection deviceConnection, int index, String string) {
        deviceConnection.controlTransfer(0x40, 52, 0, index, string.getBytes(), string.length(), 100);
    }

    //usb设备连接通道
    private UsbDeviceConnection mUsbDeviceConnection;
    //usb接口
    private UsbInterface mUsbInterface;
    //usb输出端点
    private UsbEndpoint mUsbEndpointOut;
    //usb输入端点
    private UsbEndpoint mUsbEndpointIn;

    private void initDevice() {
        /**
         * 使用usbManager遍历设备集合，通过productId找出匹配accessory模式的设备
         */
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        Collection<UsbDevice> values = deviceList.values();
        if (!values.isEmpty()) {
            for (UsbDevice usbDevice : values) {
                int productId = usbDevice.getProductId();
                if (productId == 0x2D00 || productId == 0x2D01) {
                    if (mUsbManager.hasPermission(usbDevice)) {
                        //获取当前usb设备的通讯连接
                        mUsbDeviceConnection = mUsbManager.openDevice(usbDevice);
                        if (mUsbDeviceConnection != null) {
                            //获取通讯连接接口
                            mUsbInterface = usbDevice.getInterface(0);
                            //获取通讯连接端点数量
                            int endpointCount = mUsbInterface.getEndpointCount();
                            for (int i = 0; i < endpointCount; i++) {
                                //遍历所有端点，找到输入端点与输出端点
                                UsbEndpoint usbEndpoint = mUsbInterface.getEndpoint(i);
                                if (usbEndpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                                    if (usbEndpoint.getDirection() == UsbConstants.USB_DIR_OUT) {
                                        mUsbEndpointOut = usbEndpoint;//赋值输出端点
                                    } else if (usbEndpoint.getDirection() == UsbConstants.USB_DIR_IN) {
                                        mUsbEndpointIn = usbEndpoint;//赋值输入端点
                                    }
                                }
                            }
                            //当输出端点和输入端点都不为空时，表示usb连接成功,初始化完成，可以进行数据收发
                            if (mUsbEndpointOut != null && mUsbEndpointIn != null) {
                                mHandler.sendEmptyMessage(INIT_SUCCESS);
                            }
                        }
                    } else {
                        //申请usb权限
                        mUsbManager.requestPermission(usbDevice, PendingIntent.getBroadcast(mContext, 0, new Intent(USB_ACTION), 0));
                    }
                }
            }
        } else {
            //初始化失败
            mHandler.sendEmptyMessage(INIT_FAILED);
        }
    }

    /**
     * 关闭usb连接
     * 释放所有资源
     */
    @Override
    public void closeCommunication() {
        if (mUsbDeviceConnection != null) {
            mUsbDeviceConnection.releaseInterface(mUsbInterface);
            mUsbDeviceConnection.close();
            mUsbDeviceConnection = null;
        }
        mUsbEndpointIn = null;
        mUsbEndpointOut = null;
        isReceiverMessage = false;
        mContext.unregisterReceiver(mUsbDetachedReceiver);
        mContext.unregisterReceiver(mOpenDevicesReceiver);
    }

    /**
     * 发送数据接口实现
     * 发送byte[]类型数据
     * 通过回调监听成功或失败
     * @param bytes
     * @param listener
     */
    @Override
    public void sendMessage(final byte[] bytes, SendMessageListener listener) {
        this.sendMessageListener = listener;
        if (bytes != null) {
            /**
             * 耗时操作在子线程中执行
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    /**
                     * 发送数据的地方 , 只接受byte数据类型的数据
                     */
                    int i = mUsbDeviceConnection.bulkTransfer(mUsbEndpointOut, bytes, bytes.length, 3000);
                    if (i > 0) {//大于0表示发送成功
                        mHandler.sendEmptyMessage(SEND_MESSAGE_SUCCESS);
                    } else {
                        mHandler.sendEmptyMessage(SEND_MESSAGE_FAILED);
                    }
                }
            }).start();
        } else {
            listener.onFailed("发送数据为null");

        }
    }

    //接收数据循环变量,usb连接成功后需要一直监听用户发送的数据
    private boolean isReceiverMessage = true;
    //暂定的接收数据的大小，需要优化
    private byte[] mBytes = new byte[1024];


    /**
     * 接收数据的实现
     * 通过回调来返回接收的数据
     * 使用handler来传递接收的数据到主线程
     * @param listener
     */
    @Override
    public void receiveMessage(final ReceiverMessageListener listener) {
        this.receiverMessageListener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                while (isReceiverMessage) {
                    /**
                     * 循环接受数据的地方 , 只接受byte数据类型的数据
                     */
                    if (mUsbDeviceConnection != null && mUsbEndpointIn != null) {
                        int i = mUsbDeviceConnection.bulkTransfer(mUsbEndpointIn, mBytes, mBytes.length, 3000);
                        if (i > 0) {
                            Message message = Message.obtain();
                            message.what = RECEIVER_MESSAGE_SUCCESS;
                            message.obj = mBytes;
                            mHandler.sendMessage(message);
                        }
                    } else {
                        mHandler.sendEmptyMessage(RECEIVER_MESSAGE_FAILED);
                    }
                }
            }
        }).start();
    }

    /**
     * 主设备开启成功，进行从设备唤醒
     *
     * @param usbDevice
     */
    @Override
    public void openAccessoryModel(UsbDevice usbDevice) {
        initAccessory(usbDevice);
    }

    /**
     * 主设备权限被拒绝
     */
    @Override
    public void openDevicesError() {
        Toast.makeText(mContext, "USB权限被拒绝", Toast.LENGTH_SHORT).show();
    }

    /**
     * usb连接断开 释放资源
     */
    @Override
    public void usbDetached() {
        if (mUsbDeviceConnection != null) {
            mUsbDeviceConnection.releaseInterface(mUsbInterface);
            mUsbDeviceConnection.close();
            mUsbDeviceConnection = null;
        }
        mUsbEndpointIn = null;
        mUsbEndpointOut = null;
//        mToggle = false;
        isReceiverMessage = false;
        mContext.unregisterReceiver(mUsbDetachedReceiver);
        mContext.unregisterReceiver(mOpenDevicesReceiver);
    }

}
