package com.example.demowork1.anim

import android.animation.*
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.demowork1.util.LogUtil


class PointAnimView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val DEFAULT_RADIUS = 20.0
    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var linePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var color: Int = 0
    var radius: Double = 10.0
    var currentPoint: Point = Point(DEFAULT_RADIUS, height / 2f + DEFAULT_RADIUS)
    var pointStart: Point = Point(DEFAULT_RADIUS, DEFAULT_RADIUS)
    var pointEnd: Point = Point(DEFAULT_RADIUS, DEFAULT_RADIUS)

    init {
        mPaint.color = Color.TRANSPARENT
        linePaint.color = Color.BLACK
        linePaint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas?) {
        drawCircle(canvas)
        drawLine(canvas)
        super.onDraw(canvas)
    }

    fun setAnimation() {
        pointEnd.x = width - DEFAULT_RADIUS
        pointEnd.y = height - DEFAULT_RADIUS
        //指定View的动画轨迹
        var valueAnimator: ValueAnimator =
            ValueAnimator.ofObject(PointSinEvaluator(), pointStart, pointEnd)
        valueAnimator.repeatCount = -1
        valueAnimator.repeatMode = ValueAnimator.REVERSE
        valueAnimator.addUpdateListener {
            currentPoint = it.animatedValue as Point
            postInvalidate()
        }

        var animColor = ObjectAnimator.ofObject(
            mPaint,
            "color", ArgbEvaluator(),Color.BLACK, Color.YELLOW, Color.BLUE, Color.GRAY, Color.GREEN
        )
        animColor.repeatCount = -1
        animColor.repeatMode = ValueAnimator.REVERSE

        var scaleAnim = ObjectAnimator.ofFloat(20f, 5f, 40f, 10f, 30f)
        scaleAnim.repeatCount = -1
        scaleAnim.repeatMode = ValueAnimator.REVERSE
        scaleAnim.duration = 5000
        scaleAnim.addUpdateListener {
            radius = (it.animatedValue as Float).toDouble()
        }
        var animSet = AnimatorSet()
        animSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                LogUtil.instance.d("动画开始")
            }
        })
        animSet.play(valueAnimator).with(scaleAnim).with(animColor)
        animSet.duration = 5000
        animSet.interpolator = TestTimeInterpolator()
        animSet.start()
    }

    private fun drawLine(canvas: Canvas?) {
        canvas?.drawLine(10f, height / 2f, width.toFloat(), height / 2f, linePaint)
        canvas?.drawLine(10f, height / 2f - 150, 10f, height / 2f + 150, linePaint)
        canvas?.drawPoint(currentPoint.x.toFloat(), currentPoint.y.toFloat(), linePaint)
    }

    private fun drawCircle(canvas: Canvas?) {
        var x = currentPoint.x
        canvas?.drawCircle(x.toFloat(), currentPoint.y.toFloat(), radius.toFloat(), mPaint)
    }
}