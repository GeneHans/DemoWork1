package com.example.demowork1.util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

object AdbUtil {
    /**
     * 无返回值
     */
    fun actionAdbShell(cmd: String) {
        var s = StringBuilder()
        try {
            var process: Process = Runtime.getRuntime().exec(" $cmd")
            var inputStream = process.inputStream
            var bufferedReader = BufferedReader(InputStreamReader(inputStream))
            try {
                process.waitFor()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                s.append(line).append("\n");
                line = bufferedReader.readLine()
            }
            inputStream.close()
            bufferedReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d("异常信息：" + e.message)
        }
        LogUtil.d("执行结果：$s")
    }

    /**
     * 返回值为adb执行结果
     */
    fun resultAdbShell(cmd: String): String {
        var s = StringBuilder()
        try {
            var process: Process = Runtime.getRuntime().exec(" $cmd")
            var inputStream = process.inputStream
            var bufferedReader = BufferedReader(InputStreamReader(inputStream))
            try {
                process.waitFor()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                s.append(line).append("\n");
                line = bufferedReader.readLine()
            }
            inputStream.close()
            bufferedReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d("异常信息：" + e.message)
        }
        LogUtil.d("执行结果：$s")
        return s.toString()
    }
}