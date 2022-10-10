package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TeePreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, TeePreferenceInitializer())
            .commit()
    }
}