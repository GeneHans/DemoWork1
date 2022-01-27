package com.example.common.util

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.common.util.LogUtil.d
import java.io.*
import java.util.regex.Pattern

object DeviceInfoUtil {
    var IMEI = "imei"
    var MAC: String? = null
    private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private const val KEY_DISPLAY = "ro.build.display.id"
    private const val KEY_EMUI = "ro.build.version.emui"

    /**
     * 获取屏幕信息（分辨率、密度）
     */
    fun getScreenInfo(context: Context): String {
        var screen = ""
        var density = "" //屏幕密度
        var resolution = "" // 屏幕分辨率
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        density = dm.density.toString() // 屏幕密度（0.75 / 1.0 / 1.5 / 2.0）
        resolution = dm.heightPixels.toString() + "*" + dm.widthPixels.toString()
        screen = "屏幕分辨率：  $resolution， 屏幕密度： $density"
        return screen
    }

    /**
     * 获取CPU最大频率
     *
     * @return
     */
    // "/system/bin/cat" 命令行
    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
    val maxCpuFreq: String
        get() {
            var result = ""
            val cmd: ProcessBuilder
            try {
                val args = arrayOf("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq")
                cmd = ProcessBuilder(*args)
                val process = cmd.start()
                val `in` = process.inputStream
                val re = ByteArray(24)
                while (`in`.read(re) != -1) {
                    result = result + String(re)
                }
                `in`.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                result = "N/A"
            }
            return result.trim { it <= ' ' }
        }

    /**
     * 获取CPU最小频率
     *
     * @return
     */
    val minCpuFreq: String
        get() {
            var result = ""
            val cmd: ProcessBuilder
            try {
                val args = arrayOf("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq")
                cmd = ProcessBuilder(*args)
                val process = cmd.start()
                val `in` = process.inputStream
                val re = ByteArray(24)
                while (`in`.read(re) != -1) {
                    result = result + String(re)
                }
                `in`.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                result = "N/A"
            }
            return result.trim { it <= ' ' }
        }

    /**
     * 获取CPU当前频率
     *
     * @return
     */
    val curCpuFreq: String
        get() {
            var result = "N/A"
            try {
                val fr = FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq")
                val br = BufferedReader(fr)
                val text = br.readLine()
                result = text.trim { it <= ' ' }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return result
        }

    /**
     * 获取CPU名称（在不同的机器上参数不一致？）
     *
     * @return
     */
    val cpuName: String?
        get() {
            try {
                val fr = FileReader("/proc/cpuinfo")
                val br = BufferedReader(fr)
                val text = br.readLine()
                val array = text.split(":\\s+".toRegex(), 2).toTypedArray()
                for (i in array.indices) {
                }
                return array[1]
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

    /**
     * 获取CPU完整信息
     * @return
     */
    val cpuINfo: String?
        get() {
            try {
                val fr = FileReader("/proc/cpuinfo")
                val br = BufferedReader(fr)
                var result = ""
                var text = ""
                while (br.readLine().also { text = it } != null) {
                    d(text)
                    result += """
                        $text
                        
                        """.trimIndent()
                }
                return result
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }//Print exception
//            Log.d(TAG, "CPU Count: Failed.");
    //Default to return 1 core
//Get directory containing CPU info
    //Filter to only list the devices we care about
    //            Log.d(TAG, "CPU Count: "+files.length);
    //Return the number of cores (virtual CPU devices)
//Check if filename is "cpu", followed by a single digit number//Private Class to display only CPU devices in the directory listing

    /**
     * 获取CPU核心数
     *
     * @return
     */
    val numCores: Int
        get() {
            //Private Class to display only CPU devices in the directory listing
            class CpuFilter : FileFilter {
                override fun accept(pathname: File): Boolean {
                    //Check if filename is "cpu", followed by a single digit number
                    return if (Pattern.matches("cpu[0-9]", pathname.name)) {
                        true
                    } else false
                }
            }
            return try {
                //Get directory containing CPU info
                val dir = File("/sys/devices/system/cpu/")
                //Filter to only list the devices we care about
                val files = dir.listFiles(CpuFilter())
                //            Log.d(TAG, "CPU Count: "+files.length);
                //Return the number of cores (virtual CPU devices)
                files.size
            } catch (e: Exception) {
                //Print exception
                //            Log.d(TAG, "CPU Count: Failed.");
                e.printStackTrace()
                //Default to return 1 core
                1
            }
        }

    /**
     * 获取内存信息
     *
     * @return
     */
    fun getMemoryInfo(context: Context): String {
        //获取运行内存的信息
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = ActivityManager.MemoryInfo()
        manager.getMemoryInfo(info)
        val sb = StringBuilder()
        sb.append("可用RAM:")
        sb.append(info.availMem.toString() + "B")
        sb.append(",总RAM:")
        sb.append(info.totalMem.toString() + "B")
        sb.append("\r\n")
        sb.append(Formatter.formatFileSize(context, info.availMem))
        sb.append(",")
        sb.append(Formatter.formatFileSize(context, info.totalMem))
        return sb.toString()
    }

    /**
     * 获取剩余SD卡存储空间的大小
     *
     * @return
     */
    fun getAvailSDSize(context: Context?): String {
        try {
            //首先指定需要获取大小的目录
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()

            //得到可用区块
            val availableBlocks = stat.availableBlocks.toLong()
            val availsd = blockSize * availableBlocks
            return Formatter.formatFileSize(context, availsd)
        } catch (e: Exception) {
        }
        return "get no data"
    }

    /**
     * 获取全部SD卡存储空间大小
     *
     * @return
     */
    fun getAllSDSize(context: Context?): String {
        try {
            //首先指定需要获取大小的目录
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()

            //得到全部区块
            val availableBlocks = stat.blockCount.toLong()
            val availsd = blockSize * availableBlocks
            return Formatter.formatFileSize(context, availsd)
        } catch (e: Exception) {
        }
        return "get no data"
    }

    /**
     * 获取手机型号
     */
    val model: String
        get() = Build.MANUFACTURER + " " + Build.MODEL

    /**
     * 获取OS信息，全称
     */
    val osInfo: String
        get() = "Android " + Build.VERSION.RELEASE

    /**
     * 获取CPU架构信息,支持的指令集
     * @return
     */
    val cPUStruct: String
        get() {
            var abis = arrayOf<String?>()
            abis = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Build.SUPPORTED_ABIS
            } else {
                arrayOf(Build.CPU_ABI, Build.CPU_ABI2)
            }
            val abiStr = StringBuilder()
            for (abi in abis) {
                abiStr.append(abi)
                abiStr.append(';')
            }
            return abiStr.toString()
        }
}