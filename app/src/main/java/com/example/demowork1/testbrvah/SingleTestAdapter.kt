package com.example.demowork1.testbrvah

import android.widget.Button
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.demowork1.R

class SingleTestAdapter(layoutId: Int, data: List<TestData>) :
    BaseQuickAdapter<TestData, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder?, item: TestData?) {
        helper?.setText(R.id.text_item_test, item?.textMessage ?: "get null data")
        helper?.setText(R.id.btn_item_test,item?.btnText ?:"测试按钮")
        helper?.getView<Button>(R.id.btn_item_test)?.setOnClickListener {
            Toast.makeText(mContext, "点击了按钮:" + helper.layoutPosition, Toast.LENGTH_SHORT).show()
        }
    }
}