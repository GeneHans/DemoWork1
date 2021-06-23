package com.example.demowork1.testListPages

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.demowork1.DemoApplication
import com.example.demowork1.R
import com.example.demowork1.room.DemoWorkDataBase
import com.example.demowork1.room.TestListPageEntity
import com.example.demowork1.util.LogUtil
import kotlinx.coroutines.launch


class TestListPageActivity : AppCompatActivity() {
    private lateinit var list: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var btnClear:Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var num: Long = 0
    var testListPageAdapter = TestListPageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_list_page)
        list = findViewById(R.id.page_list)
        btnAdd = findViewById(R.id.btn_add_list_data)
        btnClear = findViewById(R.id.btn_delete_list_data)
        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        //初始化列表布局
        initListView()
        //初始化点击事件
        initClickListener()
        initPositionalObserve()
    }

    /**
     * 初始化列表布局
     */
    private fun initListView(){
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.color_868DA3))
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.colorPrimaryDark))
        swipeRefreshLayout.setOnRefreshListener {
            Toast.makeText(this, "下拉刷新", Toast.LENGTH_SHORT).show()
            testListPageAdapter.refresh()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 3000)
        }
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list.addItemDecoration(
                DividerItemDecoration(
                        this,
                        DividerItemDecoration.VERTICAL
                )
        ) //添加每个item中间的分隔符
        list.adapter = testListPageAdapter
    }

    /**
     * 初始化点击事件
     */
    private fun initClickListener(){
        btnAdd.setOnClickListener {
            Thread(Runnable {
                DemoWorkDataBase.getInstance(DemoApplication.mContext).getTestPageDataDao()
                        .insertTestPageData(TestListPageEntity(num, "title$num", "content$num"))
                num++
                LogUtil.instance.d("插入数据成功")
            }).start()
        }
        btnClear.setOnClickListener {
            Thread(Runnable {
                DemoWorkDataBase.getInstance(DemoApplication.mContext).getTestPageDataDao()
                        .deleteAllData()
                LogUtil.instance.d("删除数据成功")
            }).start()
        }
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
        viewModel.getData().observe(this, Observer {
            lifecycleScope.launch {
                testListPageAdapter.submitData(it)
            }
        })
    }

}