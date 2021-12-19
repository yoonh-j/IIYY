package com.yoond.iiyy.views

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.yoond.iiyy.R

class Preference : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}