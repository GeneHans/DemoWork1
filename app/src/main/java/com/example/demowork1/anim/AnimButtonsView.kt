package com.example.demowork1.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.*
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.demowork1.DemoApplication
import com.example.demowork1.R
import com.example.demowork1.util.LogUtil

class AnimButtonsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var btnAlpha: Button? = null
    private var btnRotation: Button? = null
    private var btnTran: Button? = null
    private var btnScale: Button? = null
    private var btnXml: Button? = null
    private var btnAlphaScale: Button? = null
    private var btnTranRotation: Button? = null
    private var view: View? = null
    private var animationType: Int = 0

    init {
        View.inflate(context, R.layout.layout_btns_anim, this)
        initView()
    }

    fun setView(view: View?, animType: Int) {
        this.view = view
        this.animationType = animType
    }

    private fun initView() {
        btnAlpha = findViewById(R.id.btn_alpha)
        btnRotation = findViewById(R.id.btn_rotation)
        btnTran = findViewById(R.id.btn_translation)
        btnScale = findViewById(R.id.btn_scale)
        btnXml = findViewById(R.id.btn_xml)
        btnAlphaScale = findViewById(R.id.btn_alpha_scale)
        btnTranRotation = findViewById(R.id.btn_translation_rotation)
        btnXml?.setOnClickListener {
            if (animationType == FLASH_ANIMATION) {
                setFlashXMLAnim(view)
            } else {
                LogUtil.instance.d("无xml动画")
            }
        }
        btnAlpha?.setOnClickListener {
            when (animationType) {
                FLASH_ANIMATION -> {
                    setFlashAlphaAnim(view)
                }
                PROPERTY_ANIMATION -> {
                    setAlphaProAnimation(view)
                }
                else -> {
                    LogUtil.instance.d("没有找到动画类型")
                }
            }

        }
        btnScale?.setOnClickListener {
            when (animationType) {
                FLASH_ANIMATION -> {
                    setFlashScaleAnim(view)
                }
                PROPERTY_ANIMATION -> {
                    setScaleProAnimation(view)
                }
                else -> {
                    LogUtil.instance.d("没有找到动画类型")
                }
            }

        }
        btnTran?.setOnClickListener {
            when (animationType) {
                FLASH_ANIMATION -> {
                    setFlashTranAnim(view)
                }
                PROPERTY_ANIMATION -> {
                    setTranProAnimation(view)
                }
                else -> {
                    LogUtil.instance.d("没有找到动画类型")
                }
            }

        }
        btnRotation?.setOnClickListener {
            when (animationType) {
                FLASH_ANIMATION -> {
                    setFlashRotationAnim(view)
                }
                PROPERTY_ANIMATION -> {
                    setRotationProAnimation(view)
                }
                else -> {
                    LogUtil.instance.d("没有找到动画类型")
                }
            }
        }
        btnAlphaScale?.setOnClickListener {
            when (animationType) {
                FLASH_ANIMATION -> {
                    setFlashAnim1(view)
                }
                PROPERTY_ANIMATION -> {
                    startSetProAnimation(view)
                }
                else -> {
                    LogUtil.instance.d("没有找到动画类型")
                }
            }
        }
        btnTranRotation?.setOnClickListener {
            when (animationType) {
                FLASH_ANIMATION -> {
                    setFlashAnim2(view)
                }
                PROPERTY_ANIMATION -> {
                    startPropertyValueAnimation(view)
                }
                else -> {
                    LogUtil.instance.d("没有找到动画类型")
                }
            }
        }
    }

    /**
     * 透明度属性动画
     */
    private fun setAlphaProAnimation(v: View?) {
        if (v != null) {
            val anim = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f, 0f, 1f)
            anim.duration = 3000
            anim.start()
        }
    }

    /**
     * 缩放属性动画
     */
    private fun setScaleProAnimation(v: View?) {
        if (v != null) {
            val anim = ValueAnimator.ofFloat(0f, 1f)
            anim.duration = 3000
            anim.addUpdateListener {
                val value = it.animatedValue
                v.scaleX = value as Float
                v.scaleY = value as Float
            }
            anim.start()
        }
    }

    /**
     * 平移属性动画
     */
    private fun setTranProAnimation(v: View?) {
        if (v != null) {
            val anim = ObjectAnimator.ofFloat(v, "translationX", 0f, 100f)
            anim.duration = 1000
            anim.start()
        }
    }

    /**
     * 旋转属性动画
     */
    private fun setRotationProAnimation(v: View?) {
        if (v != null) {
            val anim = ObjectAnimator.ofFloat(v, "rotation", 0f, 90f)
            anim.duration = 1000
            anim.start()
        }
    }

    /**
     * 利用set来执行多组动画，以实现透明度和缩放为例
     */
    private fun startSetProAnimation(v: View?) {
        if (v != null) {
            val animAlpha = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f)
            val animScaleX = ObjectAnimator.ofFloat(v, "scaleX", 0f, 1f)
            val animScaleY = ObjectAnimator.ofFloat(v, "scaleY", 0f, 1f)
            val set = AnimatorSet()
            set.duration = 1000
            //with为同时执行
            set.play(animAlpha).with(animScaleX).with(animScaleY)
            //alpha在X之前执行
//            set.play(animAlpha).before(animScaleX)
            //顺序执行
//            set.playSequentially(animAlpha,animScaleX,animScaleY)
            //同时执行
//            set.playTogether(animAlpha,animScaleX,animScaleY)

            set.start()
        }
    }

    /**
     * 一个属性动画实现多个效果的变换,以实现平移和旋转为例
     *
     * @param v
     */
    private fun startPropertyValueAnimation(v: View?) {
        if (v != null) {
            val scaleXProper = PropertyValuesHolder.ofFloat("translationX", 0f, 100f)
            val scaleYProper = PropertyValuesHolder.ofFloat("rotation", 0f, 90f)
            val animation =
                ObjectAnimator.ofPropertyValuesHolder(v, scaleXProper, scaleYProper)
            animation.duration = 1000
            animation.start()
        }
    }

    /**
     * 值均衡变动
     */
    private fun setValueAnimation() {
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.duration = 3000

        anim.addUpdateListener {
            val currentValue = it.animatedValue
            LogUtil.instance.d("current value is $currentValue")
        }
        anim.start()
    }

    /**
     * xml补间动画
     */
    private fun setFlashXMLAnim(view: View?) {
        val animation: Animation =
            AnimationUtils.loadAnimation(DemoApplication.mContext, R.anim.test_flash_anim1)
        view?.startAnimation(animation)
    }

    /**
     * 设置补间Alpha动画
     */
    private fun setFlashAlphaAnim(view: View?) {
        var animation: AlphaAnimation = AlphaAnimation(0f, 1.0f)
        animation.duration = 1000
        view?.startAnimation(animation)
    }

    /**
     * java实现缩放补间动画
     */
    private fun setFlashScaleAnim(view: View?) {
        var animation: ScaleAnimation = ScaleAnimation(0f, 1.0f, 0f, 1.0f, 0.5f, 0.5f)
        animation.duration = 1000
        view?.startAnimation(animation)
    }

    private fun setFlashTranAnim(view: View?) {
        var animation = TranslateAnimation(0f, 100f, 0f, 100f)
        animation.duration = 1000
        view?.startAnimation(animation)
    }

    private fun setFlashRotationAnim(view: View?) {
        var animation = RotateAnimation(0f, 90f, 0.5f, 0.5f)
        animation.duration = 1000
        view?.startAnimation(animation)
    }

    /**
     * java实现组合补间动画,实现透明度和缩放
     */
    private fun setFlashAnim1(view: View?) {
        var animationSet: AnimationSet = AnimationSet(true)
        var animation: ScaleAnimation = ScaleAnimation(0f, 1.0f, 0f, 1.0f, 0.5f, 0.5f)
        var animation2: AlphaAnimation = AlphaAnimation(0f, 1f)
        animationSet.addAnimation(animation)
        animationSet.addAnimation(animation2)
        animationSet.duration = 1000
        view?.startAnimation(animationSet)
    }

    /**
     * java实现组合组件动画，实现平移和旋转
     */
    private fun setFlashAnim2(view: View?) {
        var animationSet = AnimationSet(true)
        var animation1 = TranslateAnimation(0f, 100f, 0f, 100f)
        var animation2 = RotateAnimation(0f, 90f, 0.5f, 0.5f)
        animationSet.addAnimation(animation1)
        animationSet.addAnimation(animation2)
        animationSet.duration = 1000
        view?.startAnimation(animationSet)
    }

    companion object {
        const val PROPERTY_ANIMATION = 1
        const val FLASH_ANIMATION = 2
    }

}