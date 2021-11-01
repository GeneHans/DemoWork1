package com.example.singlejavawork;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.singlejavawork.base.BaseActivity;
import com.example.singlejavawork.databinding.ActivityMainLibJavaBinding;
import com.example.singlejavawork.util.ConstUtil;

@Route(path = ConstUtil.MainActivityPath)
public class MainLibJavaActivity extends BaseActivity<ActivityMainLibJavaBinding> {

    @Override
    protected ActivityMainLibJavaBinding getBinding() {
        return ActivityMainLibJavaBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(getBinding().content.getId(),new Test1Fragment()).commitAllowingStateLoss();
    }
}