package com.madgical.saarthiassessments.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.madgical.saarthiassessments.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }
}