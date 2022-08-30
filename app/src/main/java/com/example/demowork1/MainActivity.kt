package com.example.demowork1

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.util.PermissionManager
import com.example.demowork1.annotation.AnnotationTestActivity
import com.example.demowork1.constraintlayout.TestConstraintLayoutActivity
import com.example.demowork1.coordinatorlayout.TestCoorActivity
import com.example.demowork1.database.sqlite.SQLiteTestActivity
import com.example.demowork1.headList.RecyclerViewTestActivity
import com.example.demowork1.mvvm.TestMvvmActivity
import com.example.demowork1.notification.NotificationActivity
import com.example.demowork1.sensor.SensorTestActivity
import com.example.demowork1.service.ServiceActivity
import com.example.demowork1.testListPages.TestListPageActivity
import com.example.demowork1.testbrvah.SingleTestAdapter
import com.example.demowork1.testbrvah.SingleTestEntity
import com.example.demowork1.usb.UsbTestActivity
import com.example.demowork1.util.LogUtil
import com.example.demowork1.util.NetUtil
import com.example.demowork1.util.PathUtil

class MainActivity : AppCompatActivity() {

    //原生的RecyclerView
    private val ITEM_RECYCLERVIEW = 0
    //dataBinding测试demo
    private val ITEM_MVVM = 1
    //USB连接测试
    private val ITEM_USB = 2
    //SQLite
    private val ITEM_SQLITE = 3
    //Android动画
    private val ITEM_ANIM = 4
    //android列表分页
    private val ITEM_PAGE_LIST = 5
    //注解学习
    private val ITEM_ANNOTATION = 6
    //通知练习
    private val ITEM_NOTIFICATION = 7
    //摇一摇demo
    private val ITEM_SENSOR = 8
    //二维码扫描
    private val ITEM_QR_CODE = 9
    //CoordinatorLayout练习
    private val ITEM_COORDINATORLAYOUT = 10
    //简单内容练习
    private val ITEM_INTENT_SERVICE = 11
    //ConstraintLayout
    private val ITEM_CONSTRAINTLAYOUT = 12
    private var listView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var permissionManager = PermissionManager.getInstance(this)
//        permissionManager.requestPermission()
//        permissionManager.requestSettingCanDrawOverlays()
//        permissionManager.checkInstallPermission()
        listView = findViewById(R.id.list_main)
        var listData = initData()
        setSingleTestAdapter(listData)
    }

    private fun setSingleTestAdapter(listData: ArrayList<SingleTestEntity>) {
        var singleAdapter = SingleTestAdapter(R.layout.item_single_test_layout, listData)
        singleAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                ITEM_RECYCLERVIEW -> {
                    var intent = Intent()
                    intent.setClass(this, RecyclerViewTestActivity::class.java)
                    startActivity(intent)
                }
                ITEM_MVVM -> {
                    var intent = Intent()
                    intent.setClass(this, TestMvvmActivity::class.java)
                    startActivity(intent)
                }
                ITEM_USB ->{
                    var intent = Intent()
                    intent.setClass(this, UsbTestActivity::class.java)
                    startActivity(intent)
                }
                ITEM_SQLITE -> {
                    var intent = Intent()
                    intent.setClass(this, SQLiteTestActivity::class.java)
                    startActivity(intent)
                }
                ITEM_ANIM -> {
                    ARouter.getInstance().build( PathUtil.ITEM_ANIM).navigation()
                }
                ITEM_PAGE_LIST ->{
                    var intent = Intent()
                    intent.setClass(this,TestListPageActivity::class.java)
                    startActivity(intent)
                }
                ITEM_ANNOTATION ->{
                    var intent = Intent()
                    intent.setClass(this,AnnotationTestActivity::class.java)
                    startActivity(intent)
                }
                ITEM_NOTIFICATION ->{
                    var intent = Intent()
                    intent.setClass(this,NotificationActivity::class.java)
                    startActivity(intent)
                }
                ITEM_SENSOR ->{
                    var intent = Intent()
                    intent.setClass(this,SensorTestActivity::class.java)
                    startActivity(intent)
                }
                ITEM_QR_CODE ->{
                    ARouter.getInstance().build(com.example.singlework.util.ConstUtil.QRCodeActivityPath).navigation()
                }
                ITEM_COORDINATORLAYOUT ->{
                    var intent = Intent()
                    intent.setClass(this, TestCoorActivity::class.java)
                    startActivity(intent)
                }
                ITEM_INTENT_SERVICE ->{
                    var intent = Intent()
                    intent.setClass(this, ServiceActivity::class.java)
                    startActivity(intent)
                }
                ITEM_CONSTRAINTLAYOUT -> {
                    var intent = Intent()
                    intent.setClass(this, TestConstraintLayoutActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    LogUtil.d("当前位置：$position")
                }
            }
        }
        listView?.adapter = singleAdapter
        listView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        listView?.layoutManager = LinearLayoutManager(this)
    }

    private fun initData(): ArrayList<SingleTestEntity> {
        var listData = ArrayList<SingleTestEntity>()
        listData.add(SingleTestEntity("原生的RecyclerView", getString(R.string.recyclerview_content)))
        listData.add(SingleTestEntity("dataBinding测试demo", getString(R.string.dataBinding_content)))
        listData.add(SingleTestEntity("USB链接测试","USB链接"))
        listData.add(SingleTestEntity("SQLite", getString(R.string.sqlite_content)))
        listData.add(SingleTestEntity("Android动画", getString(R.string.anim_content)))
        listData.add(SingleTestEntity("android列表分页", getString(R.string.list_page_content)))
        listData.add(SingleTestEntity("android注解学习", getString(R.string.annotation_content)))
        listData.add(SingleTestEntity("android通知学习", getString(R.string.notification_test_content)))
        listData.add(SingleTestEntity("摇一摇Demo", getString(R.string.annotation_content)))
        listData.add(SingleTestEntity("二维码扫描", getString(R.string.qr_code_content)))
        listData.add(SingleTestEntity("CoordinatorLayout", getString(R.string.coordinator_content)))
        listData.add(SingleTestEntity("IntentService", getString(R.string.intent_service_content)))
        listData.add(SingleTestEntity("ConstraintLayout", getString(R.string.constraintLayout_content)))
        return listData
    }

    private fun setNetListener() {
        var request = NetworkRequest.Builder().build()
        var connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr.registerNetworkCallback(request, NetUtil.instance)
    }

    /**
     * 打开其他应用
     */
    private fun openOtherApp(){
        var intent=packageManager.getLaunchIntentForPackage("com.xxxx.xxxxx")
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}