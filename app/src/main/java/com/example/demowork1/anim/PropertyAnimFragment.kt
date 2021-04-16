package com.example.demowork1.anim

import android.animation.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.demowork1.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [PropertyAnimFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PropertyAnimFragment : Fragment() {

    private var btn1: Button? = null
    private var btn2: Button? = null
    private var btn3: Button? = null
    private var pointAnimView: PointAnimView? = null
    private var ll1: LinearLayout? = null
    private var hasAddView = false
    private var animButtonsView: AnimButtonsView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property_anim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn1 = view.findViewById(R.id.btn_pro_anim1)
        btn2 = view.findViewById(R.id.btn_pro_anim2)
        btn3 = view.findViewById(R.id.btn_pro_anim3)
        ll1 = view.findViewById(R.id.ll_property_anim)
        pointAnimView = PointAnimView(view.context)
        var layoutTransition = LayoutTransition()
        ll1?.layoutTransition = layoutTransition

        animButtonsView = AnimButtonsView(view.context)

        var appearAnim = ObjectAnimator.ofFloat(null, "alpha", 0f, 1f)
        appearAnim.duration = 2000//layoutTransition.getDuration(LayoutTransition.APPEARING)
        layoutTransition.setAnimator(LayoutTransition.APPEARING, appearAnim)

        var disappearAnim = ObjectAnimator.ofFloat(null, "alpha", 1f, 0f)
        disappearAnim.duration = 2000//layoutTransition.getDuration(LayoutTransition.DISAPPEARING)
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim)

        btn1?.setOnClickListener {
            if (!hasAddView) {
                ll1?.removeAllViews()
                ll1?.addView(animButtonsView)
                hasAddView = true
            } else {
                hasAddView = false
                ll1?.removeAllViews()
            }
        }
        btn2?.setOnClickListener {
            ll1?.removeAllViews()
            ll1?.addView(pointAnimView)
        }
        btn3?.setOnClickListener {
            pointAnimView?.setAnimation()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PropertyAnimFragment().apply {}
    }
}