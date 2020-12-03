package com.example.demowork1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(arrayData: ArrayList<String>) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    private var listData: List<String> = arrayData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_test_layout, parent, false)
        var viewHolder = TestViewHolder(view)
        viewHolder.btnTest?.setOnClickListener {
            Toast.makeText(parent.context, "点击了按钮", Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.textView?.text = listData[position]
    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        var btnTest: Button? = null

        init {
            textView = itemView.findViewById(R.id.text_item_test)
            btnTest = itemView.findViewById(R.id.btn_item_test)
        }
    }
}