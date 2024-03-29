package com.example.demowork1.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author hangang.
 * _@date: Created on 2022/8/29.
 * _@description:用来监听系统下发的usb设备断开连接的广播
 */
public class UsbDetachedReceiver extends BroadcastReceiver {

    private UsbDetachedListener mUsbDetachedListener; //usb连接断开的回调

    /**
     * 构造方法中添加回调参数
     * @param usbDetachedListener
     */
    public UsbDetachedReceiver(UsbDetachedListener usbDetachedListener) {
        mUsbDetachedListener = usbDetachedListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mUsbDetachedListener.usbDetached();
    }

    public interface UsbDetachedListener {
        /**
         * usb断开连接
         */
        void usbDetached();
    }
}