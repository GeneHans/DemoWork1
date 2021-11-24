package com.example.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding?> : Fragment() {
    var binding: VB? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = initViewBinding(inflater, container, savedInstanceState)
        return binding!!.root
    }

    protected abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): VB
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}