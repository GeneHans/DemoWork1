package com.example.demowork1

import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Process
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.example.demowork1.util.LogUtil
import java.lang.reflect.Field
import java.lang.reflect.Method

class PermissionManager(var mActivity: Activity) {

    private var permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.INSTALL_PACKAGES
    )

    /**
     * 请求所有权限
     */
    fun requestPermission() {
        ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_PERMISSION_CODE)
    }

    /**
     * 请求指定权限
     * @param permission:需要请求的权限
     * @param requestCode:请求权限的code
     */
    fun requestPermission(permission: String, requestCode: Int = REQUEST_PERMISSION_CODE) {
        ActivityCompat.requestPermissions(mActivity, permissions, requestCode)
    }

    /**
     * 请求权限失败之后的操作
     * @param requestCode:请求权限的code
     */
    fun requestPermissionFailed(requestCode: Int = REQUEST_PERMISSION_CODE) {

    }

    /**
     * 检查是否有应用内安装权限
     */
    fun checkInstallPermission() {
        val haveInstallPermission: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            haveInstallPermission = mActivity.packageManager.canRequestPackageInstalls()
            if (!haveInstallPermission) {
                //没有权限让调到设置页面进行开启权限；
                val packageURI = Uri.parse("package:" + mActivity.packageName)
                val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
                mActivity.startActivityForResult(intent, 10086)
            } else {
                //有权限
            }
        } else {
            LogUtil.instance.toast("低于Android8.0，可以直接安装", mActivity)
            //其他android版本，可以直接执行安装逻辑；
        }
    }

    /**
     * 申请悬浮窗权限
     */
    fun requestSettingCanDrawOverlays() {
        if (!checkFloatPermission(mActivity)) {
            val sdkInt = Build.VERSION.SDK_INT
            val packageURI = Uri.parse("package:" + mActivity.packageName)
            if (sdkInt >= Build.VERSION_CODES.O) { //8.0以上
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, packageURI)
                mActivity.startActivityForResult(intent, 1111)
            } else if (sdkInt >= Build.VERSION_CODES.M) { //6.0-8.0
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, packageURI)
                intent.data = Uri.parse("package:" + mActivity.packageName)
                mActivity.startActivityForResult(intent, 1111)
            } else { //4.4-6.0一下
                //无需处理了
            }
        } else {
        }
    }

    /***
     * 检查悬浮窗开启权限
     * @param context
     * @return
     */
    fun checkFloatPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return true
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                var cls = Class.forName("android.content.Context")
                val declaredField: Field = cls.getDeclaredField("APP_OPS_SERVICE")
                declaredField.setAccessible(true)
                var obj: Any? = declaredField.get(cls) as? String ?: return false
                val str2 = obj as String
                obj = cls.getMethod("getSystemService", String::class.java).invoke(context, str2)
                cls = Class.forName("android.app.AppOpsManager")
                val declaredField2: Field = cls.getDeclaredField("MODE_ALLOWED")
                declaredField2.setAccessible(true)
                val checkOp: Method =
                        cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String::class.java)
                val result =
                        checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName()) as Int
                result == declaredField2.getInt(cls)
            } catch (e: Exception) {
                false
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val appOpsMgr = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                        ?: return false
                val mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", Process.myUid(), context.packageName)
                Settings.canDrawOverlays(context) || mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED
            } else {
                Settings.canDrawOverlays(context)
            }
        }
    }

    companion object {
        fun getInstance(activity: Activity): PermissionManager {
            return PermissionManager(activity)
        }

        const val REQUEST_PERMISSION_CODE = 1
    }

}