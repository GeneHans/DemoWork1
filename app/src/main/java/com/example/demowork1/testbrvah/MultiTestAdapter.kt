package com.example.demowork1.testbrvah

import android.widget.Button
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.demowork1.R
import com.example.demowork1.util.LogUtil

class MultiTestAdapter(listData: List<MultiTestEntity>) :
        BaseMultiItemQuickAdapter<MultiTestEntity, BaseViewHolder>(listData) {

    init {
        addItemType(MultiTestEntity.TYPE_1, R.layout.item_test_layout)
        addItemType(MultiTestEntity.TYPE_2, R.layout.item2_test_layout)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiTestEntity?) {
        if (item?.itemType == null)
            return
        when (item.itemType) {
            MultiTestEntity.TYPE_1 -> {
                helper?.setText(R.id.text_item_test, item.textData)
                helper?.getView<Button>(R.id.btn_item_test)?.setOnClickListener {
                    when (helper.layoutPosition) {
                        POSITION_RECYCLERVIEW -> {
                        }
                        else -> {

                        }
                    }
                }
            }
            MultiTestEntity.TYPE_2 -> {
                helper?.setText(R.id.text2_item_test, item.textData)
                helper?.getView<Button>(R.id.btn2_item_test)?.setOnClickListener {
                    LogUtil.instance.d("点击了样式2里的按钮" + helper.layoutPosition)
                }
            }
            else -> {
                LogUtil.instance.d("错误类型")
            }
        }
    }
    companion object{
        const val POSITION_RECYCLERVIEW = 1
    }
}