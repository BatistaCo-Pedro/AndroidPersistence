package com.example.myapplication

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class TeePreferenceInitializer : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.activity_tee_preferences)
    }
}