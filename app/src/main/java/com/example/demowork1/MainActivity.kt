package com.example.demowork1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demowork1.headList.RecyclerViewTestActivity
import com.example.demowork1.headList.TestAdapter
import com.example.demowork1.litepal.LitePalActivity
import com.example.demowork1.mvvm.TestMvvmActivity
import com.example.demowork1.testbrvah.MultiTestAdapter
import com.example.demowork1.testbrvah.MultiTestEntity
import com.example.demowork1.testbrvah.SingleTestAdapter
import com.example.demowork1.testbrvah.TestData
import com.example.demowork1.util.LogUtil

class MainActivity : AppCompatActivity() {
    private var listView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.list_main)
        var listData = initData()
        var listEntity = ArrayList<MultiTestEntity>()
        setMultiTestAdapter(listEntity)
    }

    private fun setMultiTestAdapter(listData: ArrayList<MultiTestEntity>) {
        var listEntity = listData
        listEntity.add(MultiTestEntity("原生RecyclerView", "跳转", MultiTestEntity.TYPE_1))
        listEntity.add(MultiTestEntity("dataBinding测试demo", "跳转", MultiTestEntity.TYPE_1))
        listEntity.add(MultiTestEntity("litePal数据库", "跳转", MultiTestEntity.TYPE_1))
        listEntity.add(MultiTestEntity("test4", "跳转", MultiTestEntity.TYPE_1))
        var multiTestAdapter = MultiTestAdapter(listEntity)
        listView?.adapter = multiTestAdapter
        multiTestAdapter?.setOnItemClickListener { adapter, view, position ->
            when (position) {
                ITEM_RECYCLERVIEW -> {
                    var intent = Intent()
                    intent.setClass(this, RecyclerViewTestActivity::class.java)
                    startActivity(intent)
                }
                ITEM_MVVM ->{
                    var intent = Intent()
                    intent.setClass(this,TestMvvmActivity::class.java)
                    startActivity(intent)
                }
                ITEM_LITE_PAL ->{
                    var intent = Intent()
                    intent.setClass(this,LitePalActivity::class.java)
                    startActivity(intent)
                }
                else ->{
                    LogUtil.instance.d("当前位置：$position")
                }
            }
        }
        listView?.layoutManager = LinearLayoutManager(this)
    }

    private fun setSingleTestAdapter(listData: ArrayList<TestData>) {
        var singleAdapter = SingleTestAdapter(R.layout.item_test_layout, listData)
        singleAdapter.setOnItemClickListener { adapter, view, position ->
            LogUtil.instance.d(position.toString())
        }
        listView?.adapter = singleAdapter
        listView?.layoutManager = LinearLayoutManager(this)
    }

    private fun initData(): ArrayList<TestData> {
        var listData = ArrayList<TestData>()
        listData.add(TestData("test1", "测试1"))
        listData.add(TestData("test2", "测试2"))
        listData.add(TestData("test3", "测试3"))
        listData.add(TestData("test4", "测试4"))
        LogUtil.instance.d(listData.toString())
        return listData
    }

    private fun setSimpleAdapter(listData: ArrayList<TestData>) {
        var testAdapter = TestAdapter(listData)
        listView?.adapter = testAdapter
        listView?.layoutManager = LinearLayoutManager(this)
    }
    private val ITEM_RECYCLERVIEW = 0
    private val ITEM_MVVM = 1
    private val ITEM_LITE_PAL = 2
}