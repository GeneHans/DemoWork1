package com.example.demowork1.anim

import android.animation.TimeInterpolator
import android.animation.TypeEvaluator
import com.example.demowork1.util.LogUtil
import kotlin.math.sin

class Point(var x: Double, var y: Double) {
    override fun toString(): String {
        return "x:$x     y:$y"
    }
}

//轨迹
class PointSinEvaluator : TypeEvaluator<Any?> {
    override fun evaluate(fraction: Float, startValue: Any?, endValue: Any?): Any? {
        val startPoint = startValue as Point?
        val endPoint = endValue as Point?
        return if (startPoint != null && endPoint != null) {
            val x = (startPoint.x + fraction * (endPoint.x - startPoint.x))
            val y = ((sin(x * Math.PI / 180) * 100) + endPoint.y / 2.0)
            Point(x, y)
        } else {
            Point(0.0, 0.0)
        }
    }
}

//时间插值器
class TestTimeInterpolator : TimeInterpolator {
    override fun getInterpolation(input: Float): Float {
        //先慢后快
        LogUtil.instance.d(input.toString())
        return input * input
    }
}
