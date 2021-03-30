package com.example.demowork1.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.contentValuesOf
import com.example.demowork1.DemoApplication


class NetUtil : ConnectivityManager.NetworkCallback() {

    //网络连接成功
    override fun onAvailable(network: Network) {
        LogUtil.instance.d("网络连接成功")
        super.onAvailable(network)
    }

    //网络已断开连接
    override fun onLost(network: Network) {
        LogUtil.instance.d("网络已断开连接")
        super.onLost(network)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        LogUtil.instance.d("网络正在断开连接")
        super.onLosing(network, maxMsToLive)
    }

    //无网络
    override fun onUnavailable() {
        LogUtil.instance.d("网络连接超时或者网络连接不可达")
        super.onUnavailable()
    }

    //当网络状态修改（网络依然可用）时调用
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        LogUtil.instance.d("net status change! 网络连接改变")
        // 表明此网络连接成功验证
        // NET_CAPABILITY_INTERNET：表示是否连接到互联网，即是否连接上了WIFI或者移动蜂窝网络，这个为TRUE不一定能正常上网
        // NET_CAPABILITY_VALIDATED：表示是否确实能和连接的互联网通信，这个为TRUE，才是真的能上网
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                // 使用WI-FI
                LogUtil.instance.d("当前在使用WiFi上网")
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                // 使用数据网络
                LogUtil.instance.d("当前在使用数据网络上网")
            } else {
                LogUtil.instance.d("当前在使用其他网络")
                // 未知网络，包括蓝牙、VPN等
            }
        }
    }

    //当访问的网络被阻塞或者解除阻塞时调用
    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        super.onBlockedStatusChanged(network, blocked)
    }

    //当网络连接属性发生变化时调用
    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties)
    }


    /**
     * 判断网络是否连接
     */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network)
                        ?: return false
                if (status.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 判断是否是WiFi连接
     */
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mWiFiNetworkInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network)
                        ?: return false
                if (status.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 判断是否是数据网络连接
     */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mMobileNetworkInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network)
                        ?: return false
                status.transportInfo
                if (status.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                }
            }
        }
        return false
    }

    companion object {
        val instance by lazy { NetUtil() }
    }
}