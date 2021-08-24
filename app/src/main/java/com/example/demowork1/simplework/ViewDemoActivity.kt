package com.example.demowork1.simplework

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.demowork1.DemoApplication
import com.example.demowork1.R
import com.example.demowork1.room.DemoWorkDataBase
import com.example.demowork1.util.LogUtil
import com.example.demowork1.util.RSACipherUtil

class ViewDemoActivity : AppCompatActivity() {

    private var text = "Hello"
    private lateinit var tvData:TextView
    private var UIHandler = Handler(Looper.getMainLooper()){
        when(it.what){
            2 ->{
                //更新UI
                tvData.text = text
                LogUtil.instance.toast("UI更新",DemoApplication.mContext)
            }
            else ->{

            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_demo)
        tvData = findViewById(R.id.tv_ht)

        var data = RSACipherUtil.instance.encrypt("这是个测试")
        var message = RSACipherUtil.instance.decrypt(data)
        LogUtil.instance.d(message?:"no data")

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
        tvData.setOnClickListener {
            mHandler.sendEmptyMessage(1)
        }

    }
}