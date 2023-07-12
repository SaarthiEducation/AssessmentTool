package com.madgical.saarthiassessments.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.madgical.saarthiassessments.R

class HomeScreen : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lottieAnimationView = view.findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        lottieAnimationView.playAnimation() // Start the animation
        view.findViewById<Button>(R.id.button_start).setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_testScreen)
        }
    }
}