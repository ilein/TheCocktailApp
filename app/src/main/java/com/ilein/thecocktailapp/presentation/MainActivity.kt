package com.ilein.thecocktailapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.ilein.thecocktailapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.main_activity)
    }
}