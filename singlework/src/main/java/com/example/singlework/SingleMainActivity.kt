package com.example.singlework

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.singlework.base.BaseActivity
import com.example.singlework.databinding.ActivitySingleMainBinding
import com.example.singlework.util.ConstUtil

@Route(path = ConstUtil.SingleMainActivityPath)
class SingleMainActivity : BaseActivity<ActivitySingleMainBinding>() {

    override var viewBinding: ActivitySingleMainBinding = ActivitySingleMainBinding.inflate(layoutInflater)

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(SingleMainLifeCycle())
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)){

        }
        viewBinding.textView.setOnClickListener {
            Toast.makeText(mActivity, "$count", Toast.LENGTH_SHORT).show()
            count++
        }
    }

}