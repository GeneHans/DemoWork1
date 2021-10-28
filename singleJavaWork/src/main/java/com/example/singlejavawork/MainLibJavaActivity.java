package com.example.singlejavawork;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.singlejavawork.base.BaseActivity;
import com.example.singlejavawork.databinding.ActivityMainLibJavaBinding;
import com.example.singlejavawork.util.ConstUtil;

@Route(path = ConstUtil.MainActivityPath)
public class MainLibJavaActivity extends BaseActivity<ActivityMainLibJavaBinding> {

    private ActivityMainLibJavaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lib_java);
        binding = ActivityMainLibJavaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportFragmentManager().beginTransaction().replace(binding.content.getId(),new Test1Fragment()).commitAllowingStateLoss();
    }
}