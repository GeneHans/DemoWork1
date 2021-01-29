package com.example.demowork1.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.util.*
import kotlin.system.exitProcess

class CrashHandler(var mContext: Context) : Thread.UncaughtExceptionHandler {
    var threadCrashHandler: Thread.UncaughtExceptionHandler? = null

    //用来存储设备信息和异常信息
    private val infos: HashMap<String, String> = HashMap()

    init {
        //获取系统默认的UncaughtException处理器
        threadCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
//        handleException(e)
        LogUtil.instance.d(t.name + "   " + e.message)
        //退出程序
        Process.killProcess(Process.myPid())
        exitProcess(1)
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo(ex)
        return true
    }

    private fun saveCrashInfo(ex: Throwable?) {
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex?.printStackTrace(printWriter)
        var cause = ex?.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        //获得错误信息
        val result = writer.toString()
        //处理错误信息
    }

    private fun collectDeviceInfo(ctx: Context) {
        try {
            val pm: PackageManager = ctx.packageManager
            val pi: PackageInfo = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                infos.put("versionName", versionName)
                infos.put("versionCode", versionCode)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(CrashHandler.TAG, "an error occured when collect package info", e)
        }
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos.put(field.name, field[null].toString())
                Log.d(CrashHandler.TAG, field.name + " : " + field[null])
            } catch (e: Exception) {
                Log.e(CrashHandler.TAG, "an error occured when collect crash info", e)
            }
        }
    }


    companion object {
        fun newInstance(context: Context): CrashHandler {
            return CrashHandler(context)
        }

        const val TAG = "CrashHandler"
    }
}