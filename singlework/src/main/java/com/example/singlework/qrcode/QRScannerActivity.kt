package com.example.singlework.qrcode

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.singlework.base.BaseActivity
import com.example.singlework.base.PermissionCallback
import com.example.singlework.databinding.ActivityQRScannerBinding
import com.example.singlework.util.ConstUtil
import com.example.singlework.util.Utils
import com.king.zxing.CaptureHelper
import com.king.zxing.DecodeFormatManager
import com.king.zxing.OnCaptureCallback
import com.king.zxing.camera.CameraManager
import com.king.zxing.camera.FrontLightMode

@Route(path = ConstUtil.QRCodeActivityPath)
class QRScannerActivity : BaseActivity(),OnCaptureCallback {
    private lateinit var viewBinding: ActivityQRScannerBinding
    private lateinit var captureHelper: CaptureHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQRScannerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initQRConfig()
        checkPermission()
    }

    override fun onResultCallback(result: String?): Boolean {
        //todo 处理结果
        Toast.makeText(mActivity,result,Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onResume() {
        //注意在生命周期的captureHelper设置，否则可能出现无法扫描的问题
        captureHelper.onResume()
        super.onResume()
    }

    override fun onPause() {
        captureHelper.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        captureHelper.onDestroy()
        super.onDestroy()
    }

    /**
     * 初始化QRScanner配置
     */
    private fun initQRConfig(){
        captureHelper = CaptureHelper(this, viewBinding.svPreview, viewBinding.vfv, null)
        captureHelper.setOnCaptureCallback(this)
        captureHelper.decodeFormats(DecodeFormatManager.QR_CODE_FORMATS)
            .supportAutoZoom(true) // 自动缩放
            .fullScreenScan(true) // 全屏扫码识别
            .supportLuminanceInvert(true) // 是否支持识别反色码，黑白颜色反转，开启提高识别效率
            .continuousScan(true) // 开启连续扫描
            .autoRestartPreviewAndDecode(false) // 连续扫描开启情况下，取消自动继续扫描，自己处理完后调用restartPreviewAndDecode()
            .playBeep(true) // 播放beep声音
            .supportZoom(true) // 支持双指缩放
            .frontLightMode(FrontLightMode.OFF) // 默认关闭闪光灯
            .setOnCaptureCallback(this) // 设置回调
            .onCreate()
        val cameraManager: CameraManager = captureHelper.cameraManager
        cameraManager.setOnTorchListener { torch: Boolean ->
            viewBinding.tvTorch.isSelected = torch
            viewBinding.tvTorch.text = if (torch) "关闭照明" else "打开照明"
        }
        viewBinding.tvTorch.setOnClickListener(View.OnClickListener {
            cameraManager.setTorch(
                !viewBinding.tvTorch.isSelected
            )
        })
        viewBinding.tvTorch.post(Runnable { this.updateScanFrameLocation() })
    }

    /**
     * 更新扫描框位置
     */
    private fun updateScanFrameLocation() {
        //(327+184)/2-184
        viewBinding.vfv.setPadding(0, 0, 0, Utils.dp2px(this, 71))
        viewBinding.vfv.scannerStart = 0 // 动态改变padding时，需要设置该值为0，以触发在onDraw中对其的重新赋值
    }

    /**
     * 权限检查
     */
    private fun checkPermission() {
        if (!checkPermissions(arrayOf(Manifest.permission.CAMERA))) {
            //弹起授权弹窗
            baseRequestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                object : PermissionCallback() {
                    override fun onPermissionGranted(permissions: Array<String?>?) {}
                    override fun onPermissionDenied(permissions: Array<String?>?) {
                        Toast.makeText(mActivity,"权限被拒绝",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
        }
    }
}