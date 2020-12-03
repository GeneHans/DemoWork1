package com.example.demowork1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demowork1.util.LogUtil

class MainActivity : AppCompatActivity() {
    private var listView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.list_main)
        var listData = ArrayList<String>()
        listData.add("test1")
        listData.add("test2")
        listData.add("test3")
        listData.add("test4")
        LogUtil.instance.d(listData.toString())
        var testAdapter = TestAdapter(listData)
        listView?.adapter = testAdapter
        listView?.layoutManager = LinearLayoutManager(this)
    }
}