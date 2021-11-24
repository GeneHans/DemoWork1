package com.example.common.util

import java.text.ParseException
import java.text.SimpleDateFormat

object DateUtil {

    /**
     * 校验时间格式
     * @param dateStr:日期字符串
     * @return
     */
    fun isValidDate(dateStr: String?): Boolean {
        if(dateStr.isNullOrEmpty()){
            return false
        }
        var convertSuccess = true
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        val strDateFormat = "yyyy-MM-dd HH:mm:ss"
        val format = SimpleDateFormat(strDateFormat)
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.isLenient = false
            format.parse(dateStr)
        } catch (e: ParseException) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false
        } catch (e: NullPointerException) {
            convertSuccess = false
        }
        return convertSuccess
    }
}