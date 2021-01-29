package com.example.demowork1.testfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.demowork1.R
import com.example.demowork1.util.LogUtil

class TestFragment2:Fragment() {
    private var data = default
    private var btnTest: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test1_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var bundle = arguments
        data = bundle?.getString(key, default) ?: default
        LogUtil.instance.d("$data   fragment2  onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        btnTest = view.findViewById(R.id.btn1_test_fragment)
        btnTest?.text = "这里是fragment2"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(key,"saveInstance")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        const val key = "key"
        const val default = "default"
        val instance by lazy { TestFragment1() }
    }
}