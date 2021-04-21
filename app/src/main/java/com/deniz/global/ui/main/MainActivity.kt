package com.deniz.global.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deniz.global.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
