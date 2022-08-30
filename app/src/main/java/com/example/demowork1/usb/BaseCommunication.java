package com.example.demowork1.usb;

/**
 * @author hangang.
 * _@date: Created on 2022/8/29.
 * _@description:
 */
public interface BaseCommunication {

    /**
     * 开启通讯接口
     *
     * @return 200 表示成功开启
     */
    public void openCommunication(CommunicationListener listener);

    /**
     * 关闭通讯接口
     */
    public void closeCommunication();

    /**
     * 发送byte[]类型数据
     */
    public void sendMessage(byte[] bytes, SendMessageListener listener);

    /**
     * 接收byte[]类型数据
     */
    public void receiveMessage(ReceiverMessageListener listener);

}
