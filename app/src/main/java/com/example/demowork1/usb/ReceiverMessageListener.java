package com.example.demowork1.usb;

/**
 * @author hangang.
 * _@date: Created on 2022/8/29.
 * _@description:usb接受数据成功或失败的回调
 */
public interface ReceiverMessageListener {

    /**
     * 成功接收到数据
     *
     * @param bytes:接收到的数据
     */
    void onSuccess(byte[] bytes);

    /**
     * 接收数据失败
     *
     * @param msg
     */
    void onFailed(String msg);
}
