package com.example.demowork1.coordinatorlayout

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.base.BaseActivity
import com.example.common.util.LogUtil
import com.example.demowork1.R
import com.example.demowork1.annotation.AnnotationTestActivity
import com.example.demowork1.constraintlayout.TestConstraintLayoutActivity
import com.example.demowork1.database.dbflow.DBFlowTestActivity
import com.example.demowork1.database.litepal.LitePalActivity
import com.example.demowork1.database.sqlite.SQLiteTestActivity
import com.example.demowork1.databinding.ActivityTestCoorBinding
import com.example.demowork1.headList.RecyclerViewTestActivity
import com.example.demowork1.mvvm.TestMvvmActivity
import com.example.demowork1.notification.NotificationActivity
import com.example.demowork1.sensor.SensorTestActivity
import com.example.demowork1.simplework.ViewDemoActivity
import com.example.demowork1.testListPages.TestListPageActivity
import com.example.demowork1.testbrvah.SingleTestAdapter
import com.example.demowork1.testbrvah.SingleTestEntity
import com.google.android.material.snackbar.Snackbar

class TestCoorActivity : BaseActivity<ActivityTestCoorBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.blue_rectangle_bg)
        }
        viewBinding.navigationView.setCheckedItem(R.id.left_menu)
        viewBinding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.left_menu -> {
                    LogUtil.toast("左边",mActivity)
                }
                R.id.middle_menu -> {
                    LogUtil.toast("中间",mActivity)
                }
                R.id.right_menu -> {
                    LogUtil.toast("右边",mActivity)
                }
            }
            true
        }
        viewBinding.floatingActionButton.setOnClickListener {
            Snackbar.make(it,"删除",Snackbar.LENGTH_SHORT)
                .setAction("undo") {
                    LogUtil.toast("悬浮按钮",mActivity)
                }
                .show()
        }
        setSingleTestAdapter(initData())
    }

    private fun setSingleTestAdapter(listData: ArrayList<SingleTestEntity>) {
        var singleAdapter = SingleTestAdapter(R.layout.item_card_view, listData)
        viewBinding.listTestCoor.adapter = singleAdapter
        viewBinding.listTestCoor.layoutManager = GridLayoutManager(this,2)
    }

    private fun initData(): ArrayList<SingleTestEntity> {
        var listData = ArrayList<SingleTestEntity>()
        listData.add(SingleTestEntity("原生的RecyclerView", getString(R.string.recyclerview_content)))
        listData.add(SingleTestEntity("dataBinding测试demo", getString(R.string.dataBinding_content)))
        listData.add(SingleTestEntity("litePal数据库练习", getString(R.string.litepal_content)))
        listData.add(SingleTestEntity("ConstraintLayout", getString(R.string.constraintLayout_content)))
        listData.add(SingleTestEntity("SQLite", getString(R.string.sqlite_content)))
        listData.add(SingleTestEntity("DBFlow", getString(R.string.dbflow_content)))
        listData.add(SingleTestEntity("Android动画", getString(R.string.anim_content)))
        listData.add(SingleTestEntity("android列表分页", getString(R.string.list_page_content)))
        listData.add(SingleTestEntity("android注解学习", getString(R.string.annotation_content)))
        listData.add(SingleTestEntity("android通知学习", getString(R.string.notification_test_content)))
        listData.add(SingleTestEntity("摇一摇Demo", getString(R.string.annotation_content)))
        listData.add(SingleTestEntity("二维码扫描", getString(R.string.qr_code_content)))
        listData.add(SingleTestEntity("CoordinatorLayout", getString(R.string.coordinator_content)))
        listData.add(SingleTestEntity("简单内容练习", getString(R.string.annotation_content)))
        return listData
    }

    override fun initViewBinding() {
        viewBinding = ActivityTestCoorBinding.inflate(layoutInflater)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.test_coor_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{
                viewBinding.drawerLayout.openDrawer(GravityCompat.START)
                LogUtil.d("home")
            }
            R.id.left_menu -> {
                LogUtil.d("左边")
            }
            R.id.middle_menu -> {
                LogUtil.d("中间")
            }
            R.id.right_menu -> {
                LogUtil.d("右边")
            }
        }
        return true
    }

}