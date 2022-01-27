package com.example.common.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import static android.os.Build.MANUFACTURER;
import static android.os.Build.MODEL;

public class DeviceInfoManager {

    public static String IMEI = "imei";

    public static String MAC;
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_DISPLAY = "ro.build.display.id";
    private static final String KEY_EMUI = "ro.build.version.emui";

    /**
     * 获取屏幕信息（分辨率、密度）
     */
    public static String getScreenInfo(Context context) {
        String screen = "";
        String density = "";      //屏幕密度
        String resolution = ""; // 屏幕分辨率
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        density = String.valueOf(dm.density); // 屏幕密度（0.75 / 1.0 / 1.5 / 2.0）
        resolution = String.valueOf(dm.heightPixels) + "*" + String.valueOf(dm.widthPixels);
        screen = "屏幕分辨率：  " + resolution + "， 屏幕密度： " + density;
        return screen;
    }

    /**
     * 获取CPU最大频率
     *
     * @return
     */
    // "/system/bin/cat" 命令行
    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 获取CPU最小频率
     *
     * @return
     */
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 获取CPU当前频率
     *
     * @return
     */
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取CPU名称（在不同的机器上参数不一致？）
     *
     * @return
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取CPU完整信息
     * @return
     */
    public static String getCpuINfo() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String result = "";
            String text = "";
            while (( text = br.readLine()) != null) {
                LogUtil.INSTANCE.d(text);
                result += text + "\n";
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取CPU核心数
     *
     * @return
     */
    public static int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
//            Log.d(TAG, "CPU Count: "+files.length);
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Print exception
//            Log.d(TAG, "CPU Count: Failed.");
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }

    /**
     * 获取内存信息
     *
     * @return
     */
    public static String getMemoryInfo(Context context) {
        //获取运行内存的信息
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        StringBuilder sb = new StringBuilder();
        sb.append("可用RAM:");
        sb.append(info.availMem + "B");
        sb.append(",总RAM:");
        sb.append(info.totalMem + "B");
        sb.append("\r\n");
        sb.append(Formatter.formatFileSize(context, info.availMem));
        sb.append(",");
        sb.append(Formatter.formatFileSize(context, info.totalMem));
        return sb.toString();
    }

    /**
     * 获取剩余SD卡存储空间的大小
     *
     * @return
     */
    public static String getAvailSDSize(Context context) {
        try {
            //首先指定需要获取大小的目录
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();

            //得到可用区块
            long availableBlocks = stat.getAvailableBlocks();

            long availsd = blockSize * availableBlocks;
            return Formatter.formatFileSize(context, availsd);
        } catch (Exception e) {
        }
        return "get no data";
    }

    /**
     * 获取全部SD卡存储空间大小
     *
     * @return
     */
    public static String getAllSDSize(Context context) {
        try {
            //首先指定需要获取大小的目录
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();

            //得到全部区块
            long availableBlocks = stat.getBlockCount();

            long availsd = blockSize * availableBlocks;
            return Formatter.formatFileSize(context, availsd);
        } catch (Exception e) {
        }
        return "get no data";
    }

    /**
     * 获取手机Model型号
     */
    public static String getModel() {
        return MANUFACTURER + " " + MODEL;
    }

    /**
     * 获取OS信息，全称
     */
    public static String getOsInfo() {
        return "Android " + Build.VERSION.RELEASE;
    }

    /**
     * 获取CPU架构信息,支持的指令集
     * @return
     */
    public static String getCPUStruct() {
        String[] abis = new String[]{};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        StringBuilder abiStr = new StringBuilder();
        for (String abi : abis) {
            abiStr.append(abi);
            abiStr.append(';');
        }
        return abiStr.toString();
    }
}