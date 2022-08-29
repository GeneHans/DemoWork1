package com.example.common.manager.dialog;

/**
 * @author hangang.
 * _@date: Created on 2022/8/21.
 * _@description:窗口参数类
 */
public class WindowWrapper {

    //窗口
    private IWindow mWindow;
    //优先级 值越大优先级越高
    private int mPriority;
    //当前是否正在展示
    private boolean isShowing;
    //是否可以展示
    private boolean isCanShow;
    //弹窗类型
    private WindowTaskManager.WindowType mWindowType;
    //弹窗名称
    private String mWindowName;

    private WindowWrapper(Builder builder) {
        mWindow = builder.window;
        mPriority = builder.priority;
        mWindowType = builder.windowType;
        isCanShow = builder.isCanShow;
        mWindowName = builder.windowName;
    }

    public IWindow getWindow() {
        return mWindow;
    }

    public void setWindow(IWindow window) {
        this.mWindow = window;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        this.mPriority = priority;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public WindowTaskManager.WindowType getWindowType() {
        return mWindowType;
    }

    public void setWindowType(WindowTaskManager.WindowType mWindowType) {
        this.mWindowType = mWindowType;
    }

    public boolean isCanShow() {
        return isCanShow;
    }

    public void setCanShow(boolean canShow) {
        isCanShow = canShow;
    }

    public String getWindowName() {
        return mWindowName;
    }

    public void setWindowName(String mWindowName) {
        this.mWindowName = mWindowName;
    }

    public static class Builder {

        //窗口
        private IWindow window;
        //优先级，值越大优先级越高
        private int priority;
        //弹窗类型
        private WindowTaskManager.WindowType windowType;
        //是否可以展示
        private boolean isCanShow;
        //弹窗名称
        private String windowName;

        public Builder window(IWindow window) {
            this.window = window;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder windowType(WindowTaskManager.WindowType type) {
            this.windowType = type;
            return this;
        }

        public Builder setCanShow(boolean canShow) {
            isCanShow = canShow;
            return this;
        }

        public String getWindowName() {
            return windowName;
        }

        public Builder setWindowName(String windowName) {
            this.windowName = windowName;
            return this;
        }

        public WindowWrapper build() {
            return new WindowWrapper(this);
        }

    }

}
