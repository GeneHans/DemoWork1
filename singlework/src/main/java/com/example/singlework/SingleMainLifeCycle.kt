package com.example.singlework

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class SingleMainLifeCycle : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onActivityOnCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onActivityOnResume() {

    }
}