package com.example.demowork1.headList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demowork1.R
import com.example.demowork1.testbrvah.TestData
import com.example.demowork1.util.LogUtil

class TestSimpleAdapter(arrayData: ArrayList<TestData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listData: List<TestData> = arrayData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_test_layout, parent, false)
        return TestViewHolder1(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TestViewHolder1) {
            holder.textView?.text = listData[position].textMessage
            holder.btnTest?.setOnClickListener {
                LogUtil.instance.d("点击了第 $position 个按钮")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    class TestViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        var btnTest: Button? = null

        init {
            textView = itemView.findViewById(R.id.text_item_test)
            btnTest = itemView.findViewById(R.id.btn_item_test)
        }
    }

    companion object{
        const val TYPE_1 = 0
        const val TYPE_2 = 1
    }
}