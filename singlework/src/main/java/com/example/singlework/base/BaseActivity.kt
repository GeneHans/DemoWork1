package com.example.singlework.base

import android.app.Activity
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding

open class BaseActivity : AppCompatActivity() {
    lateinit var mActivity: BaseActivity

    inline fun <reified VB : ViewBinding> Activity.inflate() = lazy {
        inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
    }

    inline fun <reified VB : ViewBinding> Dialog.inflate() = lazy {
        inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
        VB::class.java.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        when(requestCode){
            REQ_CODE_PERMISSION -> {
                
            }
            else ->{

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