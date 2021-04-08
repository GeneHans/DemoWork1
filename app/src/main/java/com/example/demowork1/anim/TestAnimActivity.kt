package com.example.demowork1.anim

import android.R.attr.animation
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Looper.loop
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.demowork1.R


class TestAnimActivity : AppCompatActivity() {

    private var imageAnimFrame1: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_anim)
        imageAnimFrame1 = findViewById(R.id.img_anim1)
        setFrameAnimation()
    }

    fun setProAnimation(){

    }

//    private fun RotateAnimation() {
//        var deValue = 0f
//        var pxValue = 100f
//        var pyValue = 100f
//        var timeValue = 1000L
//        val animation = RotateAnimation(
//            -deValue, deValue, Animation.RELATIVE_TO_SELF,
//            pxValue, Animation.RELATIVE_TO_SELF, pyValue
//        )
//        animation.duration = timeValue
//        animation.fillAfter = keep.isChecked()
//        if (loop.isChecked()) {
//            animation.repeatCount = -1
//        } else {
//            animation.repeatCount = 0
//        }
//        if (reverse.isChecked()) {
//            animation.repeatMode = Animation.REVERSE
//        } else {
//            animation.repeatMode = Animation.RESTART
//        }
//        img.startAnimation(animation)
//    }

    /**
     * 补间动画
     */
    private fun setAnimation(){
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)
        imageAnimFrame1?.startAnimation(animation)
    }

    /**
     * 逐帧动画
     */
    fun setFrameAnimation(){
        imageAnimFrame1?.setImageResource(R.drawable.test_frame_anim2)
        val animationDrawable1 = imageAnimFrame1?.drawable as AnimationDrawable?
        animationDrawable1?.start()
    }
}