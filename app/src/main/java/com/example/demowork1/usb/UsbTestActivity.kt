package com.example.demowork1.usb

import android.os.Bundle
import android.util.Base64
import com.example.common.base.BaseActivity
import com.example.demowork1.databinding.ActivityUsbTestBinding
import com.example.demowork1.util.LogUtil

/**
 * @author hangang.
 *         _@date: Created on 2022/8/30.
 *         _@description:
 */
class UsbTestActivity : BaseActivity<ActivityUsbTestBinding>() {
    lateinit var usbCommunication: UsbCommunication
    override fun initViewBinding() {
        viewBinding = ActivityUsbTestBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usbCommunication = UsbCommunication.getInstance(this)
        viewBinding.btnConnectUsb.setOnClickListener {
            usbCommunication.openCommunication(object : CommunicationListener {
                override fun onSuccess(code: Int, msg: String?) {
                    LogUtil.d("connect USB success")
                    getUSBMessage()
                }

                override fun onFailed(msg: String?) {
                    LogUtil.d("connect USB failed")
                }

            })
        }
    }

    fun getUSBMessage() {
        usbCommunication.receiveMessage(object : ReceiverMessageListener {
            override fun onSuccess(bytes: ByteArray?) {
                LogUtil.d("receive Message success" + Base64.encodeToString(bytes, Base64.NO_WRAP))
            }

            override fun onFailed(msg: String?) {
                LogUtil.d("receive Message failed ;$msg")
            }

        })
    }

}