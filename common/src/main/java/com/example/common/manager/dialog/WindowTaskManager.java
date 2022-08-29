package com.example.common.manager.dialog;

import android.app.Activity;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hangang.
 * _@date: Created on 2022/8/21.
 * _@description:
 */

public class WindowTaskManager {

    public static final int DIALOG = 0;
    public static final int POUPOWINDOW = 1;
    public static final int OTHERS = 2;

    @IntDef({DIALOG, POUPOWINDOW, OTHERS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WindowType {
    }

    private List<WindowWrapper> mWindows;

    private static WindowTaskManager mDefaultInstance;

    private WindowTaskManager() {
    }

    //单例
    public static WindowTaskManager getInstance() {
        if (mDefaultInstance == null) {
            synchronized (WindowTaskManager.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new WindowTaskManager();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * 添加弹窗
     *
     * @param windowWrapper 待显示的弹窗
     */
    public synchronized void addWindow(Activity activity, WindowWrapper windowWrapper) {
        if (windowWrapper != null) {
            if (mWindows == null) {
                mWindows = new ArrayList<>();
            }
            if (windowWrapper.getWindow() != null) {
                windowWrapper.getWindow().setOnWindowShowListener(() -> windowWrapper.setShowing(true));
                windowWrapper.getWindow().setOnWindowDismissListener(() -> {
                    windowWrapper.setShowing(false);
                    mWindows.remove(windowWrapper);
                    showNext(activity);
                });
            }
            mWindows.add(windowWrapper);
        }
    }

    /**
     * 弹窗满足展示条件，展示弹窗
     *
     * @param priority
     */
    public synchronized void enableWindow(Activity activity, int priority, IWindow window) {
        WindowWrapper windowWrapper = getTargetWindow(priority);
        if (windowWrapper != null) {
            if (windowWrapper.getWindow() == null) {
                window.setOnWindowShowListener(() -> windowWrapper.setShowing(true));
                window.setOnWindowDismissListener(() -> {
                    windowWrapper.setShowing(false);
                    mWindows.remove(windowWrapper);
                    showNext(activity);
                });
            }
            windowWrapper.setCanShow(true);
            windowWrapper.setWindow(window);
            show(activity, priority);
        }
    }

    /**
     * 移除不需要显示弹窗
     *
     * @param priority
     */
    public synchronized void disableWindow(int priority) {
        WindowWrapper windowWrapper = getTargetWindow(priority);
        if (windowWrapper != null && windowWrapper.getWindow() != null) {
            if (mWindows != null) {
                mWindows.remove(windowWrapper);
            }
        }
    }

    /**
     * 展示弹窗
     * 从优先级最高的Window开始显示
     */
    public synchronized void show(Activity activity) {
        WindowWrapper windowWrapper = getMaxPriorityWindow();
        if (windowWrapper != null && windowWrapper.isCanShow()) {
            IWindow window = windowWrapper.getWindow();
            if (window != null) {
                window.show(activity);
            }
        }
    }

    /**
     * 显示指定的弹窗
     *
     * @param priorities
     */
    public synchronized void show(Activity activity, int priorities) {
        WindowWrapper windowWrapper = getTargetWindow(priorities);
        if (windowWrapper != null && windowWrapper.getWindow() != null) {
            WindowWrapper topShowWindow = getShowingWindow();
            if (topShowWindow == null) {
                int priority = windowWrapper.getPriority();
                WindowWrapper maxPriorityWindow = getMaxPriorityWindow();
                if (maxPriorityWindow != null && windowWrapper.isCanShow() && priority >= maxPriorityWindow.getPriority()) {
                    if (windowWrapper.getWindow() != null) {
                        windowWrapper.getWindow().show(activity);
                    }
                }
            }
        }
    }

    /**
     * 清除弹窗管理者
     */
    public synchronized void clear() {
        if (mWindows != null) {
            for (int i = 0, size = mWindows.size(); i < size; i++) {
                if (mWindows.get(i) != null) {
                    IWindow window = mWindows.get(i).getWindow();
                    if (window != null) {
                        window.dismiss();
                    }
                }
            }
            mWindows.clear();
        }
        WindowHelper.getInstance().onDestroy();
    }

    /**
     * 清除弹窗管理者
     *
     * @param dismiss 是否同时dismiss掉弹窗管理者维护的弹窗
     */
    public synchronized void clear(boolean dismiss) {
        if (mWindows != null) {
            if (dismiss) {
                for (int i = 0, size = mWindows.size(); i < size; i++) {
                    if (mWindows.get(i) != null) {
                        IWindow window = mWindows.get(i).getWindow();
                        if (window != null) {
                            window.dismiss();
                        }
                    }
                }
            }
            mWindows.clear();
        }
        WindowHelper.getInstance().onDestroy();
    }

    /**
     * 展示下一个优先级最大的Window
     */

    private synchronized void showNext(Activity activity) {
        WindowWrapper windowWrapper = getMaxPriorityWindow();
        if (windowWrapper != null && windowWrapper.isCanShow()) {
            if (windowWrapper.getWindow() != null) {
                windowWrapper.getWindow().show(activity);
            }
        }
    }

    /**
     * 获取当前栈中优先级最高的Window（优先级相同则返回后添加的弹窗）
     */
    private synchronized WindowWrapper getMaxPriorityWindow() {
        if (mWindows != null) {
            int maxPriority = -1;
            int position = -1;
            for (int i = 0, size = mWindows.size(); i < size; i++) {
                WindowWrapper windowWrapper = mWindows.get(i);
                if (i == 0) {
                    position = 0;
                    maxPriority = windowWrapper.getPriority();
                } else {
                    if (windowWrapper.getPriority() >= maxPriority) {
                        position = i;
                        maxPriority = windowWrapper.getPriority();
                    }
                }
            }
            if (position != -1) {
                return mWindows.get(position);
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取当前优先级的弹窗
     *
     * @param priority
     * @return
     */
    private synchronized WindowWrapper getTargetWindow(int priority) {
        if (mWindows != null) {
            for (int i = 0, size = mWindows.size(); i < size; i++) {
                WindowWrapper windowWrapper = mWindows.get(i);
                if (windowWrapper != null && windowWrapper.getPriority() == priority) {
                    return windowWrapper;
                }
            }
        }
        return null;
    }

    /**
     * 获取当前处于show状态的弹窗
     */
    @Nullable
    private synchronized WindowWrapper getShowingWindow() {
        if (mWindows != null) {
            for (int i = 0, size = mWindows.size(); i < size; i++) {
                WindowWrapper windowWrapper = mWindows.get(i);
                if (windowWrapper != null && windowWrapper.getWindow() != null && windowWrapper.isShowing()) {
                    return windowWrapper;
                }
            }
        }
        return null;
    }
}