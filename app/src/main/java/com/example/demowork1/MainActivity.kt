package com.example.demowork1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demowork1.testbrvah.MultiTestAdapter
import com.example.demowork1.testbrvah.MultiTestEntity
import com.example.demowork1.testbrvah.SingleTestAdapter
import com.example.demowork1.util.LogUtil

class MainActivity : AppCompatActivity() {
    private var listView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.list_main)
        var listData = initData()
        var listEntity = ArrayList<MultiTestEntity>()
        listEntity.add(MultiTestEntity("test1", MultiTestEntity.TYPE_1))
        listEntity.add(MultiTestEntity("test2", MultiTestEntity.TYPE_2))
        listEntity.add(MultiTestEntity("test3", MultiTestEntity.TYPE_1))
        listEntity.add(MultiTestEntity("test4", MultiTestEntity.TYPE_2))
        var multiTestAdapter = MultiTestAdapter(listEntity)
        listView?.adapter = multiTestAdapter
        listView?.layoutManager = LinearLayoutManager(this)
    }

    private fun setSingleTestAdapter(listData: ArrayList<String>) {
        var singleAdapter = SingleTestAdapter(R.layout.item_test_layout, listData)
        singleAdapter.setOnItemClickListener { adapter, view, position ->
            LogUtil.instance.d(position.toString())
        }
        listView?.adapter = singleAdapter
        listView?.layoutManager = LinearLayoutManager(this)
    }

    private fun initData(): ArrayList<String> {
        var listData = ArrayList<String>()
        listData.add("test1")
        listData.add("test2")
        listData.add("test3")
        listData.add("test4")
        LogUtil.instance.d(listData.toString())
        return listData
    }

    private fun setSimpleAdapter(listData: ArrayList<String>) {
        var testAdapter = TestAdapter(listData)
        listView?.adapter = testAdapter
        listView?.layoutManager = LinearLayoutManager(this)
    }
}