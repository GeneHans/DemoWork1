package com.example.demowork1.anim

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.demowork1.R

/**
 * A simple [Fragment] subclass.
 * Use the [SimpleAnimFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimpleAnimFragment : Fragment() {

    private var imageAnimFrame1: ImageView? = null
    private var btnFlash: Button? = null
    private var btnProperty: Button? = null
    private var animButtonsView: AnimButtonsView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple_anim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageAnimFrame1 = view.findViewById(R.id.img_anim1)
        btnFlash = view.findViewById(R.id.btn_flash_anim)
        btnProperty = view.findViewById(R.id.btn_property_anim)
        animButtonsView = view.findViewById(R.id.anim_button_view1)
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

    companion object {
        @JvmStatic
        fun newInstance() = SimpleAnimFragment().apply {}
    }
}