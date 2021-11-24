package com.example.common.base

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var mActivity: BaseActivity<VB>
    abstract var viewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        mActivity = this
    }

    //检查是否全部授权
    open fun checkPermissions(permissions: Array<String?>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                            this,
                            permission!!
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private var permissionCallback: PermissionCallback? = null

    /**
     * 申请权限
     *
     * @param perms     权限
     * @param rationale 申请perms权限时的原因说明（如果多项，则需一同说明）
     * @param callback  申请结果回调
     */
    open fun baseRequestPermissions(perms: Array<String?>?, callback: PermissionCallback) {
        permissionCallback = callback
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkPermissions(perms!!)) {
            ActivityCompat.requestPermissions(
                    this,
                    perms,
                REQ_CODE_PERMISSION
            )
        } else {
            permissionCallback?.onPermissionGranted(perms)
        }
    }

    companion object {
        private const val REQ_CODE_PERMISSION = 101
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            REQ_CODE_PERMISSION -> {

            }
            else -> {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

abstract class PermissionCallback {
    abstract fun onPermissionGranted(permissions: Array<String?>?)
    abstract fun onPermissionDenied(permissions: Array<String?>?)
    fun interceptDeniedCusDialog(): Boolean {
        return false
    }
}