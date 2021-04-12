package com.example.demowork1.anim

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.demowork1.R


class TestAnimActivity : AppCompatActivity() {

    private var imageAnimFrame1: ImageView? = null
    private var btnFlash: Button? = null
    private var btnProperty: Button? = null
    private var animButtonsView: AnimButtonsView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_anim)
        imageAnimFrame1 = findViewById(R.id.img_anim1)
        btnFlash = findViewById(R.id.btn_flash_anim)
        btnProperty = findViewById(R.id.btn_property_anim)
        animButtonsView = findViewById(R.id.anim_button_view1)
        btnFlash?.setOnClickListener {
            animButtonsView?.setView(imageAnimFrame1, AnimButtonsView.FLASH_ANIMATION)
        }
        btnProperty?.setOnClickListener {
            animButtonsView?.setView(imageAnimFrame1, AnimButtonsView.PROPERTY_ANIMATION)
        }
    }

    /**
     * 逐帧动画
     */
    private fun setFrameAnimation() {
        imageAnimFrame1?.setImageResource(R.drawable.test_frame_anim2)
        val animationDrawable1 = imageAnimFrame1?.drawable as AnimationDrawable?
        animationDrawable1?.start()
    }
}