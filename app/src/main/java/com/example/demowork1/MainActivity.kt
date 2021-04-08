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
import com.example.demowork1.anim.TestAnimActivity
import com.example.demowork1.constraintlayout.TestConstraintLayoutActivity
import com.example.demowork1.database.dbflow.DBFlowTestActivity
import com.example.demowork1.headList.RecyclerViewTestActivity
import com.example.demowork1.database.litepal.LitePalActivity
import com.example.demowork1.manager.PermissionManager
import com.example.demowork1.mvvm.TestMvvmActivity
import com.example.demowork1.database.sqlite.SQLiteTestActivity
import com.example.demowork1.testbrvah.MultiTestAdapter
import com.example.demowork1.testbrvah.MultiTestEntity
import com.example.demowork1.testbrvah.SingleTestAdapter
import com.example.demowork1.testbrvah.SingleTestEntity
import com.example.demowork1.util.LogUtil
import com.example.demowork1.util.NetUtil

class MainActivity : AppCompatActivity() {

    private val ITEM_RECYCLERVIEW = 0
    private val ITEM_MVVM = 1
    private val ITEM_LITE_PAL = 2
    private val ITEM_CONSTRAINTLAYOUT = 3
    private val ITEM_SQLITE = 4
    private val ITEM_DBFLOW = 5
    private val ITEM_ANIM = 6
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
                ITEM_LITE_PAL -> {
                    var intent = Intent()
                    intent.setClass(this, LitePalActivity::class.java)
                    startActivity(intent)
                }
                ITEM_CONSTRAINTLAYOUT -> {
                    var intent = Intent()
                    intent.setClass(this, TestConstraintLayoutActivity::class.java)
                    startActivity(intent)
                }
                ITEM_SQLITE -> {
                    var intent = Intent()
                    intent.setClass(this, SQLiteTestActivity::class.java)
                    startActivity(intent)
                }
                ITEM_DBFLOW -> {
                    var intent = Intent()
                    intent.setClass(this, DBFlowTestActivity::class.java)
                    startActivity(intent)
                }
                ITEM_ANIM -> {
                    var intent = Intent()
                    intent.setClass(this, TestAnimActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    LogUtil.instance.d("当前位置：$position")
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
        listData.add(SingleTestEntity("litePal数据库", getString(R.string.litepal_content)))
        listData.add(
            SingleTestEntity(
                "ConstraintLayout",
                getString(R.string.constraintLayout_content)
            )
        )
        listData.add(SingleTestEntity("SQLite", getString(R.string.sqlite_content)))
        listData.add(SingleTestEntity("DBFlow", getString(R.string.dbflow_content)))
        listData.add(SingleTestEntity("Android动画", getString(R.string.anim_content)))
        return listData
    }

    private fun setMultiTestAdapter(listData: ArrayList<MultiTestEntity>) {
        var listEntity = listData
        listEntity.add(MultiTestEntity("原生RecyclerView", "跳转", MultiTestEntity.TYPE_1))
        listEntity.add(MultiTestEntity("dataBinding测试demo", "跳转", MultiTestEntity.TYPE_1))
        listEntity.add(MultiTestEntity("litePal数据库", "跳转", MultiTestEntity.TYPE_1))
        listEntity.add(MultiTestEntity("brvah", "跳转", MultiTestEntity.TYPE_1))
        var multiTestAdapter = MultiTestAdapter(listEntity)
        listView?.adapter = multiTestAdapter
        multiTestAdapter?.setOnItemClickListener { adapter, view, position ->
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
                ITEM_LITE_PAL -> {
                    var intent = Intent()
                    intent.setClass(this, LitePalActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    LogUtil.instance.d("当前位置：$position")
                }
            }
        }
        listView?.layoutManager = LinearLayoutManager(this)
    }

    private fun setNetListener() {
        var request = NetworkRequest.Builder().build()
        var connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr.registerNetworkCallback(request, NetUtil.instance)
    }
}