package com.example.demowork1.testListPages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig

class ConcertViewModel : ViewModel() {

    /**
     *      pageSize = 20  设置每一页加载长度，
     *      和 PagingSource 的 params.loadSize对应
     * */
    fun getData() = Pager(PagingConfig(pageSize = 3)) {
        MyPagingSource()
    }.flow.asLiveData()

}