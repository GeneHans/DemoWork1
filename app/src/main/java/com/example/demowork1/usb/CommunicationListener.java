package com.example.demowork1.usb;

/**
 * @author hangang.
 * _@date: Created on 2022/8/29.
 * _@description:usb连接开启成功或失败的回调
 */
public interface CommunicationListener {
    /**
     * 连接开启成功
     * @param code
     * @param msg
     */
    void onSuccess(int code, String msg);

    /**
     * 连接开启失败
     * @param msg
     */
    void onFailed(String msg);
}
