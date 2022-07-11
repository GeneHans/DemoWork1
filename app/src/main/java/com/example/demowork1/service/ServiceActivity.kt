package com.example.demowork1.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.widget.TextView
import com.example.common.base.BaseActivity
import com.example.common.util.LogUtil
import com.example.demowork1.DemoApplication
import com.example.demowork1.PersonProto
import com.example.demowork1.database.room.DemoWorkDataBase
import com.example.demowork1.databinding.ActivityServiceBinding
import com.example.demowork1.util.RSACipherUtil


class ServiceActivity : BaseActivity<ActivityServiceBinding>() {

    private var binderService: BinderService? = null

    private var isBound = false

    private var text = "Hello"
    private lateinit var tvData: TextView
    private var UIHandler = Handler(Looper.getMainLooper()){
        when(it.what){
            2 ->{
                //更新UI
                tvData.text = text
                com.example.demowork1.util.LogUtil.toast("UI更新", DemoApplication.mContext)
            }
            else ->{

            }
        }
        false
    }

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
        tvData = viewBinding.tvHt
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
        testHandlerThread()
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

    private fun testHandlerThread(){
        var data = RSACipherUtil.instance.encrypt("这是个测试")
        var message = RSACipherUtil.instance.decrypt(data)
        LogUtil.d(message?:"no data")

        var handlerThread = HandlerThread("testHandlerThread")
        handlerThread.start()
        var mHandler = Handler(handlerThread.looper, Handler.Callback {
            if(it.what == 1) {
                //子线程中进行相应的操作
                var data =
                        DemoWorkDataBase.getInstance(DemoApplication.mContext).getTestPageDataDao()
                                .queryById(0)
                text = data?.content ?: "no data"
                //主线程更新UI
                UIHandler.sendEmptyMessage(2)
            }
            false
        })
        var person1 = PersonProto.Person.newBuilder().setName("Tom")
                .setId(111).setBoo(false).setEmail("123@123.com").setPhone("123456789")
                .build()
        tvData.setOnClickListener {
            var dataTemp = PersonProto.Person.parseFrom(person1.toByteArray())
            com.example.demowork1.util.LogUtil.d(dataTemp.toString())
            mHandler.sendEmptyMessage(1)
        }
    }

    override fun initViewBinding() {
        viewBinding = ActivityServiceBinding.inflate(layoutInflater)
    }
}