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

    private var btnAddAnimView: Button? = null
    private var btnRunAnimView: Button? = null
    private var pointAnimView: PointAnimView? = null
    private var ll1: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property_anim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddAnimView = view.findViewById(R.id.btn_pro_anim_add)
        btnRunAnimView = view.findViewById(R.id.btn_pro_anim_run)
        ll1 = view.findViewById(R.id.ll_property_anim)
        pointAnimView = PointAnimView(view.context)
        var layoutTransition = LayoutTransition()
        ll1?.layoutTransition = layoutTransition

        btnAddAnimView?.setOnClickListener {
            ll1?.removeAllViews()
            ll1?.addView(pointAnimView)
        }
        btnRunAnimView?.setOnClickListener {
            pointAnimView?.setAnimation()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PropertyAnimFragment().apply {}
    }
}