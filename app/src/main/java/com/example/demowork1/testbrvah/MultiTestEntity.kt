package com.example.demowork1.testbrvah

import com.chad.library.adapter.base.entity.MultiItemEntity

class MultiTestEntity(data: String, btnText: String, type: Int) : MultiItemEntity {
    private var itemType = type
    var textData = data
    var btnTextData = btnText

    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        const val TYPE_1 = 1
        const val TYPE_2 = 2
    }
}