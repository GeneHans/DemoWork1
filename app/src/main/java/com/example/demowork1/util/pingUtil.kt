package com.example.demowork1.util

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URI
import java.util.regex.Pattern


/**
 * 网络诊断工具类，通过ping的方式来获取当前的网络状态
 */
object PingUtil {
    //ip地址正则表达式
    private const val ipRegex = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))"

    //默认超时时间
    private const val TIME_OUT = 100

    //默认ping的次数
    private const val DEFAULT_COUNT = 1

    //最小RTT
    private const val TYPE_MIN = 1

    //平均RTT
    private const val TYPE_AVG = 2

    //最大RTT
    private const val TYPE_MAX = 3

    //RTT偏差
    private const val TYPE_MDEV = 4

    //网络ping的结果
    private const val RESULT_BAD = "bad"
    private const val RESULT_NORMAL = "normal"
    private const val RESULT_GOOD = "good"

    /**
     * 解析数据，延迟小于50为good，延迟在50~100之间或者丢包率为0为normal，
     * 延迟在100以上或者返回失败或丢包率大于0，则返回bad
     *
     * @param url ping的地址
     * @return
     */
    fun parseRTT(url: String?): String {
        val pingRtt = getRTT(url)
        val pingLostRate = getPacketLossFloat(url)
        if (pingRtt > 0 && pingRtt < 50) {
            return RESULT_GOOD
        } else if (pingRtt <= 100 && pingRtt > 50 || pingLostRate == 0f) {
            return RESULT_NORMAL
        } else if (pingRtt > 100 || pingRtt < 0 || pingLostRate > 0) {
            return RESULT_BAD
        }
        return RESULT_BAD
    }

    /**
     * 解析数据，延迟小于50为good，延迟在50~100之间或者丢包率为0为normal，
     * 延迟在100以上或者返回失败或丢包率大于0，则返回bad
     *
     * @param url     ping的地址
     * @param type    ping的类型
     * @param count   ping的次数
     * @param timeout ping的超时时间
     * @return
     */
    fun parseRTT(url: String?, type: Int, count: Int, timeout: Int): String {
        val pingRtt = getRTT(url, type, count, timeout)
        val pingLostRate = getPacketLossFloat(url, count, timeout)
        if (pingRtt > 0 && pingRtt < 50) {
            return RESULT_GOOD
        } else if (pingRtt <= 100 && pingRtt > 50 || pingLostRate == 0f) {
            return RESULT_NORMAL
        } else if (pingRtt > 100 || pingRtt < 0 || pingLostRate > 0) {
            return RESULT_BAD
        }
        return RESULT_BAD
    }

    /**
     * 获取pingPTT值，默认为平均RTT
     *
     * @param url 需要ping的url地址
     * @return RTT值，单位 ms 注意：-1是默认值，返回-1表示获取失败
     */
    fun getRTT(url: String?): Int {
        return getRTT(url, TYPE_AVG)
    }

    /**
     * 获取ping的RTT值
     *
     * @param url  需要ping的url地址
     * @param type ping的类型
     * @return RTT值，单位 ms 注意：-1是默认值，返回-1表示获取失败
     */
    fun getRTT(url: String?, type: Int): Int {
        return getRTT(url, type, DEFAULT_COUNT, TIME_OUT)
    }

    /**
     * 获取ping url的RTT
     *
     * @param url     需要ping的url地址
     * @param type    ping的类型
     * @param count   需要ping的次数
     * @param timeout 需要ping的超时时间，单位 ms
     * @return RTT值，单位 ms 注意：-1是默认值，返回-1表示获取失败
     */
    fun getRTT(url: String?, type: Int, count: Int, timeout: Int): Int {
        val domain = getDomain(url) ?: return -1
        val pingString = ping(createSimplePingCommand(count, timeout, domain))
        if (null != pingString) {
            try {
                //获取以"min/avg/max/mdev"为头的文本，分别获取此次的ping参数
                val tempInfo = pingString.substring(pingString.indexOf("min/avg/max/mdev") + 19)
                val temps = tempInfo.split("/".toRegex()).toTypedArray()
                return when (type) {
                    TYPE_MIN -> {
                        Math.round(temps[0].toFloat())
                    }
                    TYPE_AVG -> {
                        Math.round(temps[1].toFloat())
                    }
                    TYPE_MAX -> {
                        Math.round(temps[2].toFloat())
                    }
                    TYPE_MDEV -> {
                        Math.round(temps[3].toFloat())
                    }
                    else -> {
                        -1
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return -1
    }

    /**
     * 获取ping url的丢包率，浮点型
     *
     * @param url 需要ping的url地址
     * @return 丢包率 如50%可得 50，注意：-1是默认值，返回-1表示获取失败
     */
    fun getPacketLossFloat(url: String?): Float {
        val packetLossInfo = getPacketLoss(url)
        if (null != packetLossInfo) {
            try {
                return packetLossInfo.replace("%", "").toFloat()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return (-1).toFloat()
    }

    /**
     * 获取ping url的丢包率，浮点型
     *
     * @param url     需要ping的url地址
     * @param count   需要ping的次数
     * @param timeout 需要ping的超时时间，单位 ms
     * @return 丢包率 如50%可得 50，注意：-1是默认值，返回-1表示获取失败
     */
    fun getPacketLossFloat(url: String?, count: Int, timeout: Int): Float {
        val packetLossInfo = getPacketLoss(url, count, timeout)
        if (null != packetLossInfo) {
            try {
                return packetLossInfo.replace("%", "").toFloat()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return (-1).toFloat()
    }

    /**
     * 获取ping url的丢包率
     *
     * @param url 需要ping的url地址
     * @return 丢包率 x%
     */
    fun getPacketLoss(url: String?): String? {
        return getPacketLoss(url, DEFAULT_COUNT, TIME_OUT)
    }

    /**
     * 获取ping url的丢包率
     *
     * @param url     需要ping的url地址
     * @param count   需要ping的次数
     * @param timeout 需要ping的超时时间，单位ms
     * @return 丢包率 x%
     */
    fun getPacketLoss(url: String?, count: Int, timeout: Int): String? {
        val domain = getDomain(url) ?: return null
        val pingString = ping(createSimplePingCommand(count, timeout, domain))
        if (null != pingString) {
            try {
                val tempInfo = pingString.substring(pingString.indexOf("received,"))
                return tempInfo.substring(9, tempInfo.indexOf("packet"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }
    // ********************以下是一些辅助方法，设为private********************//
    /**
     * 域名获取
     *
     * @param url 网址
     * @return
     */
    private fun getDomain(url: String?): String? {
        var domain: String? = null
        try {
            domain = URI.create(url).host
            if (null == domain && isMatch(ipRegex, url)) {
                domain = url
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return domain
    }

    private fun isMatch(regex: String, string: String?): Boolean {
        return Pattern.matches(regex, string)
    }

    /**
     * ping方法，调用ping指令
     *
     * @param command ping指令文本
     * @return
     */
    private fun ping(command: String): String? {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec(command) //执行ping指令
            val `is` = process.inputStream
            val reader = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var line: String?
            while (null != reader.readLine().also { line = it }) {
                sb.append(line)
                sb.append("\n")
            }
            reader.close()
            `is`.close()
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            process?.destroy()
        }
        return null
    }

    /**
     * ping指令格式文本
     *
     * @param count   调用次数
     * @param timeout 超时时间
     * @param domain  地址
     * @return
     */
    private fun createSimplePingCommand(count: Int, timeout: Int, domain: String): String {
        return "/system/bin/ping -c $count -w $timeout $domain"
    }
}
