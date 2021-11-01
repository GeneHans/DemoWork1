package com.example.singlework.util

import android.content.Context
import android.util.TypedValue
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * 工具类
 *
 * @author: chengzhen.li
 */
object Utils {

    fun formatDecimal(decimalValue: Double, decimalNum: Int): String {
        val fnum: DecimalFormat =
            NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
        if (decimalNum == 2) { // 两位小数
            fnum.applyPattern("##0.00")
        } else if (decimalNum == 0) { // 无小数
            fnum.applyPattern("##0")
        }
        val dd: String = fnum.format(decimalValue)
        return dd
    }

    fun dp2px(context: Context?, dpVal: Int): Int {
        if (context == null) return 0
        val scale: Float = context.resources.displayMetrics.density
        return (dpVal * scale + 0.5f).toInt()
    }

    fun dpToPx(context: Context?,dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context?.resources?.displayMetrics
        )
    }
    fun dp2px(context: Context?, dpVal: Float): Int {
        if (context == null) return 0
        val scale: Float = context.resources.displayMetrics.density
        return (dpVal * scale + 0.5f).toInt()
    }
}