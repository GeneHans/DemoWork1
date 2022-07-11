package com.example.demowork1.notification

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.demowork1.R
import com.example.demowork1.annotation.AnnotationTestActivity
import com.example.demowork1.constraintlayout.TestConstraintLayoutActivity
import com.example.demowork1.coordinatorlayout.TestCoorActivity
import com.example.demowork1.database.sqlite.SQLiteTestActivity
import com.example.demowork1.headList.RecyclerViewTestActivity
import com.example.demowork1.mvvm.TestMvvmActivity
import com.example.demowork1.sensor.SensorTestActivity
import com.example.demowork1.service.ServiceActivity
import com.example.demowork1.testListPages.TestListPageActivity
import com.example.demowork1.testbrvah.SingleTestAdapter
import com.example.demowork1.testbrvah.SingleTestEntity
import com.example.demowork1.util.LogUtil
import com.example.demowork1.util.PathUtil

class NotificationActivity : AppCompatActivity() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var list_notification: RecyclerView
    //常规通知
    private val ITEM_REGULAR = 0
    //大页面通知
    private val ITEM_FULL = 1
    //可点击通知
    private val ITEM_CLICK = 2
    //可回复消息通知
    private val ITEM_RESPONSE = 3
    //有进度条通知
    private val ITEM_PROCESS = 4
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        list_notification = findViewById(R.id.list_notification)

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        setSingleTestAdapter(initData())
    }

    private fun initData(): ArrayList<SingleTestEntity> {
        var listData = ArrayList<SingleTestEntity>()
        listData.add(SingleTestEntity("常规通知", ""))
        listData.add(SingleTestEntity("大页面通知", ""))
        listData.add(SingleTestEntity("可点击通知", ""))
        listData.add(SingleTestEntity("可回复消息通知", ""))
        listData.add(SingleTestEntity("下载通知", ""))
        return listData
    }

    private fun setSingleTestAdapter(listData: ArrayList<SingleTestEntity>) {
        var singleAdapter = SingleTestAdapter(R.layout.item_single_test_layout, listData)
        var notificationTest = NotificationTest.instance
        singleAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                ITEM_REGULAR -> {
                    notificationTest.createRegularNotification(position)
                }
                ITEM_FULL -> {
                  notificationTest.createFullNotification(position)
                }
                ITEM_CLICK -> {
                    notificationTest.createIntentNotification(position)
                }
                ITEM_RESPONSE -> {
                    notificationTest.createEditTextNotification(position)
                }
                ITEM_PROCESS -> {
                    notificationTest.createProgressNotification(position)
                }
                else -> {
                    LogUtil.d("当前位置：$position")
                }
            }
        }
        list_notification.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        list_notification.adapter = singleAdapter
    }
}