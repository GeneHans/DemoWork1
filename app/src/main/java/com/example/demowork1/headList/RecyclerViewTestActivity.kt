package com.example.demowork1.headList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demowork1.R
import com.example.demowork1.testbrvah.TestData
import com.example.demowork1.util.LogUtil

class RecyclerViewTestActivity : AppCompatActivity() {
    private var listView: TestHeaderRecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_test)
        listView = findViewById(R.id.list_test_recyclerView)
        var listData = initData()
        setSimpleAdapter(listData)
        var list: ListView? = null
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
        var headView = TestHeaderView(this)
        listView?.addHeaderView(headView)
        listView?.setAdapter(testAdapter)
        listView?.layoutManager = LinearLayoutManager(this)
    }
}