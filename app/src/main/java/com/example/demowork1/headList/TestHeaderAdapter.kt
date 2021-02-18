package com.example.demowork1.headList

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demowork1.R
import com.example.demowork1.util.LogUtil
import java.util.logging.Logger

class TestHeaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>, IWrapperAdapter {
    private var mAdapter: TestAdapter? = null
    private var headerViews: ArrayList<View>? = ArrayList()
    private var footerViews: ArrayList<View>? = ArrayList()
    private var currentPosition: Int = 0

    constructor(
            headViews: ArrayList<View>,
            footViews: ArrayList<View>,
            adapter: TestAdapter
    ) {
        headerViews = headViews
        footerViews = footViews
        this.mAdapter = adapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_HEADER -> {
                if (headerViews?.get(0) != null) {
                    return HeadViewHolder(headerViews!![0])
                }
            }
            TYPE_FOOTER -> {
                if (footerViews?.get(0) != null) {
                    return FootViewHolder(footerViews!![0])
                }
            }
            TYPE_INVALID -> {

            }
            else -> {
            }
        }
        return mAdapter!!.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return if (mAdapter != null)
            mAdapter!!.itemCount + getFooterCount() + getHeaderCount()
        else
            getFooterCount() + getHeaderCount()
    }

    override fun getHeaderCount(): Int {
        return headerViews?.size ?: 0
    }

    override fun getFooterCount(): Int {
        return footerViews?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        currentPosition = position
        if (currentPosition < getHeaderCount())
            return TYPE_HEADER
        if (mAdapter != null) {
            if (currentPosition < itemCount - getFooterCount()) {
                return mAdapter!!.getItemViewType(currentPosition - getHeaderCount())
            }
            return TYPE_FOOTER
        }
        return TYPE_INVALID

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var headCount = getHeaderCount()
        if (position < headCount) {
            return
        }
        var cPosition = position - headCount
        if (mAdapter != null) {
            var adapterCount = mAdapter!!.itemCount
            if (cPosition < adapterCount) {
                mAdapter!!.onBindViewHolder(holder, cPosition)
                return
            }
        }
    }

    class HeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        const val TYPE_HEADER = -1
        const val TYPE_FOOTER = -2
        const val TYPE_INVALID = -3
    }
}