package com.example.demowork1.mvvm

import android.os.Bundle
import androidx.annotation.IntDef
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.util.ObjectsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.example.demowork1.R
import com.example.demowork1.databinding.ActivityTestMvvmBinding
import org.jetbrains.annotations.NotNull

class TestMvvmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityTestMvvmBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_test_mvvm)
        binding.viewModel = TestViewModel()
        binding.lifecycleOwner = this
    }
}