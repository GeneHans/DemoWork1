package com.example.demowork1.testbrvah

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.demowork1.R

class SingleTestAdapter(layoutId: Int, data: List<SingleTestEntity>) :
    BaseQuickAdapter<SingleTestEntity, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder?, item: SingleTestEntity?) {
        helper?.setText(R.id.tv_single_type_title, item?.title ?: "没有标题")
        helper?.setText(R.id.tv_single_type_content, item?.content ?: "没有内容")
    }
}