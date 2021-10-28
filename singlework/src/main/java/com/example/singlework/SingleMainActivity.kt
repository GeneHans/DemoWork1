package com.example.singlework

import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.singlework.base.BaseActivity
import com.example.singlework.databinding.ActivitySingleMainBinding
import com.example.singlework.util.ConstUtil

@Route(path = ConstUtil.SingleMainActivityPath)
class SingleMainActivity : BaseActivity() {
    private lateinit var viewBinding: ActivitySingleMainBinding
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySingleMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.textView.setOnClickListener {
            Toast.makeText(mActivity, "$count", Toast.LENGTH_SHORT).show()
            count++
        }
    }
}