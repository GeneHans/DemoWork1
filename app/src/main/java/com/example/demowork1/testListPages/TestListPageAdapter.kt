package com.example.demowork1.testListPages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demowork1.R
import com.example.demowork1.room.TestListPageEntity

class TestListPageAdapter :
    PagingDataAdapter<TestListPageEntity, TestListPageAdapter.TestViewHolder1>(TestItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder1 {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_test_layout, parent, false)
        return TestViewHolder1(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder1, position: Int) {
        var testData = getItem(position)
        holder.btnTest?.text = testData?.title
        holder.textView?.text = testData?.content
    }

    class TestViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        var btnTest: Button? = null

        init {
            textView = itemView.findViewById(R.id.text_item_test)
            btnTest = itemView.findViewById(R.id.btn_item_test)
        }
    }

}

class TestItemCallBack : DiffUtil.ItemCallback<TestListPageEntity>() {
    override fun areItemsTheSame(
            oldItem: TestListPageEntity,
            newItem: TestListPageEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
            oldItem: TestListPageEntity,
            newItem: TestListPageEntity
    ): Boolean {
        return oldItem.title == newItem.title && oldItem.content == newItem.content
    }

}