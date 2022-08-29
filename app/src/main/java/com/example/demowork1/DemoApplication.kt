package com.example.demowork1

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.example.demowork1.database.room.DemoWorkDataBase
import com.example.demowork1.database.sqlite.SQLiteDBManager
import com.example.demowork1.util.CrashHandler
import com.example.demowork1.util.LogUtil
import org.json.JSONObject
import xcrash.ICrashCallback
import xcrash.TombstoneParser
import xcrash.XCrash
import xcrash.XCrash.InitParameters
import java.io.IOException


class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
       initXCrash()
        if(BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        var crashHandler = CrashHandler.newInstance(this)
        SQLiteDBManager.getInstance(this).createDb(1)
        Room.databaseBuilder(
            this,
            DemoWorkDataBase::class.java,
            DemoWorkDataBase.RoomDataBaseName
        )
    }

    private fun initXCrash() {
        val callback = ICrashCallback { logPath, emergency ->
            LogUtil.d("检测到错误")
            sendThenDeleteCrashLog(logPath, emergency)
        }
        val params = InitParameters()

        // 通用配置
        params.setAppVersion(BuildConfig.VERSION_NAME)
        //                .setPlaceholderCountMax(DEFAULT_PLACEHOLDER_MAX_COUNT)
//                .setPlaceholderSizeKb(DEFAULT_PLACEHOLDER_SIZE_KB)
//                .setLogFileMaintainDelayMs(DEFAULT_LOG_FILE_MAINTAIN_DELAY_MS);

        // Java 崩溃配置
        params.setJavaRethrow(true)
            .setJavaDumpFds(false) // ...
            .setJavaCallback(callback)

        // Native 崩溃配置
        params.setNativeRethrow(true) // ...
            .setNativeCallback(callback)

        // ANR 配置
        params.setAnrCallback(callback)
        XCrash.init(this, params)
    }

    private fun sendThenDeleteCrashLog(logPath: String, emergency: String) {
        // Parse
        try {
            val map = TombstoneParser.parse(logPath, emergency)
            val crashReport = JSONObject(map as Map<*, *>).toString()
            LogUtil.d("文件路径：$logPath")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //String crashReport = new JSONObject(map).toString();

        // Send the crash report to server-side.
        // ......

        // If the server-side receives successfully, delete the log file.
        //
        // Note: When you use the placeholder file feature,
        //       please always use this method to delete tombstone files.
        //
        //TombstoneManager.deleteTombstone(logPath);
    }

    companion object {
        lateinit var mContext: Context
    }
}