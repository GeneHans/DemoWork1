package com.example.demowork1.headList

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.demowork1.TestAdapter

class TestHeaderRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private var headerViews: ArrayList<View> = ArrayList()
    private var footerViews: ArrayList<View> = ArrayList()

    init {

    }

    public fun addHeaderView(headerView: View) {
        headerViews.clear()
        headerViews.add(headerView)
    }

    public fun addFooterView(footerView: View) {
        footerViews.clear()
        footerViews.add(footerView)
    }

    public fun setAdapter(testAdapter: TestAdapter) {
        var adapter = TestHeaderAdapter(headerViews, footerViews, testAdapter)
        this.adapter = adapter
    }

    companion object {

    }
}