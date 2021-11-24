package com.example.demowork1.coordinatorlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.base.BaseFragment
import com.example.demowork1.R
import com.example.demowork1.databinding.FragmentTestCoorMainBinding

class TestCoorMainFragment : BaseFragment<FragmentTestCoorMainBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_coor_main, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TestCoorMainFragment()
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTestCoorMainBinding {
        return FragmentTestCoorMainBinding.inflate(inflater,container,false)
    }
}