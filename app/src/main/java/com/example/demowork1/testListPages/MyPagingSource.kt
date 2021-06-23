package com.example.demowork1.testListPages

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.demowork1.DemoApplication
import com.example.demowork1.room.DemoWorkDataBase
import com.example.demowork1.room.TestListPageEntity
import com.example.demowork1.util.LogUtil

class MyPagingSource : PagingSource<Int, TestListPageEntity>() {

    /**
     *    params.key 是取 当前列表的起始节点
     *    params.loadSize 是获取当前列表的长度
     * */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TestListPageEntity> {
        var startPosition = params.key ?: 0
        val loadSize = params.loadSize
        // 加载的数据
        var data = getListData(startPosition, startPosition + loadSize)
        if (data == null) {
            LogUtil.instance.d("没有获取到数据")
            data = arrayListOf(TestListPageEntity(1, "1", "1"))
        }
        //防止下拉刷新起始点错误
        if (startPosition < 0) {
            startPosition = 0
        }
        LogUtil.instance.d("startPosition    $startPosition     loadSize  $loadSize     ${data.size}")
        return LoadResult.Page(
                data,
                // 往上加载的Key ,如果不可以往上加载就null
                null,
                // 加载下一页的key 如果传null就说明到底了
                if (startPosition + ConcertViewModel.pageSize >= data.size) null else startPosition + data.size
        )
    }

    override fun getRefreshKey(state: PagingState<Int, TestListPageEntity>): Int? {
        return -1
    }

    private suspend fun getListData(start: Int, end: Int): List<TestListPageEntity>? {
        return DemoWorkDataBase.getInstance(DemoApplication.mContext).getTestPageDataDao()
                .queryLowerId(start.toLong(), end.toLong())
    }
}