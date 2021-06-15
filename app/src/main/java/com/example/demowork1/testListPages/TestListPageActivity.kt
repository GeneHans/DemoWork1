package com.example.demowork1.testListPages

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demowork1.DemoApplication
import com.example.demowork1.R
import com.example.demowork1.room.DemoWorkDataBase
import com.example.demowork1.room.TestListPageEntity
import com.example.demowork1.util.LogUtil
import kotlinx.coroutines.launch


class TestListPageActivity : AppCompatActivity() {
    private lateinit var list :RecyclerView
    private lateinit var btnTest:Button
    private var num:Long = 0
    var testListPageAdapter = TestListPageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list_page)
        list = findViewById(R.id.page_list)
        btnTest = findViewById(R.id.btn_list_page)
        list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        list.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        ) //添加每个item中间的分隔符
        list.adapter = testListPageAdapter
        btnTest.setOnClickListener {
            Thread(Runnable {
                DemoWorkDataBase.getInstance(DemoApplication.mContext).getTestPageDataDao().insertTestPageData(TestListPageEntity(num, "title$num","content$num"))
                num++
                LogUtil.instance.d("插入数据成功")
            }).start()
        }
        initPositionalObserve()
    }

    private fun initPositionalObserve() {
        /**
         *      ViewModel 绑定 Activity 生命周期
         * */
        val viewModel = ViewModelProviders.of(this).get(ConcertViewModel::class.java)

        /**
         *      ViewModel 感知数据变化，更新 Adapter 列表
         *      Adapter.submitData 这是一个用 suspend 修饰的方法，所以需要开启一个有生命周期的协程
         * */
        viewModel.getData().observe(this, Observer { lifecycleScope.launch {
            testListPageAdapter.submitData(it)
        }})
    }

}