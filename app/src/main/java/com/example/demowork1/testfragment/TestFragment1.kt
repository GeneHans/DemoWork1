package com.example.demowork1.testfragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.demowork1.R
import com.example.demowork1.util.LogUtil

class TestFragment1 : Fragment() {
    private var data = default

    private var iHandleFragment: IHandleFragment? = null

    private var btnTest: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is IHandleFragment) {
            iHandleFragment = activity as IHandleFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N && activity!=null && activity!!.isInMultiWindowMode) {
            LogUtil.instance.d("更新UI")
            return inflater.inflate(R.layout.fragment_test1_layout_resize, container, false)
        }
        LogUtil.instance.d("未更新UI")
        return inflater.inflate(R.layout.fragment_test1_layout, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var bundle = arguments
        data = bundle?.getString(key, default) ?: default
        LogUtil.instance.d("$data   onViewCreated")
        if (savedInstanceState != null) {
            data = savedInstanceState.getString(key, default)
            LogUtil.instance.d("$data   getSaved  onViewCreated")
        }
        super.onViewCreated(view, savedInstanceState)
        btnTest = view.findViewById(R.id.btn1_test_fragment)
        btnTest?.setOnClickListener {
            iHandleFragment?.openFragment2()
            LogUtil.instance.d("点击了按钮    $data")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(key, "saveInstance")
        LogUtil.instance.d("onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        LogUtil.instance.d("onDestroyView")
        super.onDestroyView()
    }

    fun updateData(dataS: String) {
        data = dataS
    }

    companion object {
        const val key = "key"
        const val default = "default"
        val instance by lazy { TestFragment1() }
    }
}

interface IHandleFragment {
    fun openFragment2()
}