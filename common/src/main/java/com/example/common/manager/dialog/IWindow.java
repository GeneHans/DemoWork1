package com.example.common.manager.dialog;

import android.app.Activity;

/**
 * @author hangang.
 * _@date: Created on 2022/8/21.
 * _@description:Window管理接口
 */

public interface IWindow {

    /**
     * 展示弹窗
     * @param activity
     */
    void show(Activity activity);

    /**
     * 弹窗关闭
     */
    void dismiss();

    /**
     * 设置关闭回调
     * @param listener
     */
    void setOnWindowDismissListener(OnWindowDismissListener listener);

    /**
     * 设置展示回调
     * @param listener
     */
    void setOnWindowShowListener(OnWindowShowListener listener);

}