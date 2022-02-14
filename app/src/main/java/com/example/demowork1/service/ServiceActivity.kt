package com.example.demowork1.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import com.example.common.base.BaseActivity
import com.example.common.util.LogUtil
import com.example.demowork1.databinding.ActivityServiceBinding


class ServiceActivity : BaseActivity<ActivityServiceBinding>() {

    private var binderService: BinderService? = null

    private var isBound = false

    private val conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            isBound = true
            val myBinder = binder as MyBinder
            binderService = myBinder.getService()
            LogUtil.d("ActivityA onServiceConnected")
            val num: Int? = binderService?.getRandomNumber()
            LogUtil.d("ActivityA 中调用 TestService的getRandomNumber方法, 结果: $num")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
            LogUtil.d("ActivityA onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.btnOpenService.setOnClickListener {
            startService()
        }
        viewBinding.btnStopService.setOnClickListener {
            stopService()
        }
        viewBinding.btnBinderService.setOnClickListener {
            binderService()
        }
        viewBinding.btnUnBinderService.setOnClickListener {
            unBinderService()
        }
    }

    /**
     * 绑定服务
     */
    private fun binderService() {
        var bindIntent = Intent(this, BinderService::class.java)
        bindService(bindIntent, conn, Context.BIND_AUTO_CREATE)
    }

    /**
     * 解除Service绑定
     */
    private fun unBinderService() {
        unbindService(conn)
    }

    /**
     * 启动服务
     */
    private fun startService() {
        var intent = Intent()
        intent.setClass(this, MainService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    /**
     * 停止Service
     */
    private fun stopService() {
        var intent = Intent()
        intent.setClass(this, MainService::class.java)
        stopService(intent)
    }

    override fun initViewBinding() {
        viewBinding = ActivityServiceBinding.inflate(layoutInflater)
    }
}