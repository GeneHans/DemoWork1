package com.example.common.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.tencent.mmkv.MMKV

/**
 * SharedPreference操作类
 * 使用了MMKV库
 */
class SPManager(@NonNull var context: Context) {
    private val SP_KEY = "demoWorkSP"

    init {
        //MMKV初始化
        MMKV.initialize(context)
    }

    private fun getSP(): SharedPreferences? {
        return context.getSharedPreferences(SP_KEY, MODE_PRIVATE)
    }

    private fun getMMKV(): MMKV? {
//        var mmkv2 = MMKV.mmkvWithID("1",MMKV.MULTI_PROCESS_MODE)
        return MMKV.defaultMMKV()
    }

    /**
     * MMKV方法
     */
    fun setStringData(key: String, data: String) {
        getMMKV()?.encode(key, data)
    }

    fun getStringData(key: String, default: String): String {
        return getMMKV()?.decodeString(key, default) ?: default
    }

    /**
     * 以下方法是SP方法，已废弃
     * 方法较多，在此仅以Boolean和String为例子
     */
    private fun setBooleanSpData(key: String, data: Boolean) {
        getSP()?.edit()?.putBoolean(key, data)?.apply()
    }

    private fun getBooleanSpData(key: String, default: Boolean): Boolean {
        return getSP()?.getBoolean(key, default) ?: default
    }

    private fun setStringSpData(key: String, data: String) {
        getSP()?.edit()?.putString(key, data)?.apply()
    }

    private fun getStringSpData(key: String, default: String): String {
        return getSP()?.getString(key, default) ?: default
    }

    /**
     * 清除所有缓存
     */
    fun clearAll() {
        getSP()?.edit()?.clear()?.apply()
    }

    companion object {
        fun getInstance(context: Context): SPManager {
            return SPManager(context)
        }
    }
}