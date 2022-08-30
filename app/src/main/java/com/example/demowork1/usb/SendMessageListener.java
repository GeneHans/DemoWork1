package com.example.demowork1.usb;

/**
 * @author hangang.
 * _@date: Created on 2022/8/29.
 * _@description:usb发送数据成功或失败的回调
 */
public interface SendMessageListener {
    void onSuccess();
    void onFailed(String msg);
}
