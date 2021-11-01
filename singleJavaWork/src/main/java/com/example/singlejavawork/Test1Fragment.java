package com.example.singlejavawork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.singlejavawork.base.BaseFragment;
import com.example.singlejavawork.databinding.FragmentTest1Binding;

public class Test1Fragment extends BaseFragment<FragmentTest1Binding> {

    public Test1Fragment() {
        // Required empty public constructor
    }

    @Override
    protected FragmentTest1Binding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return FragmentTest1Binding.inflate(inflater,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是一条测试数据", Toast.LENGTH_SHORT).show();
            }
        });
    }
}