package com.example.demowork1.manager

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.demowork1.DemoApplication
import com.tencent.mmkv.MMKV


class SPManager {
    val SP_KEY = "demoWorkSP"

    private fun getSP(): SharedPreferences? {
        return DemoApplication.mContext?.getSharedPreferences(SP_KEY, MODE_PRIVATE)
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
    private fun clear() {
        getSP()?.edit()?.clear()?.apply()
    }

    companion object {
        val instance by lazy { SPManager() }
    }
}