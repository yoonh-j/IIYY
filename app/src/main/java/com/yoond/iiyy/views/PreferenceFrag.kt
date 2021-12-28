package com.yoond.iiyy.views

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference

import androidx.preference.PreferenceFragmentCompat
import com.yoond.iiyy.R
import com.yoond.iiyy.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferenceFrag : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {
    private lateinit var logoutPref: Preference
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        init()
    }

    override fun onPreferenceClick(p0: Preference?): Boolean {
        if (p0 == logoutPref) {
            showDialog()
        }
        return true
    }

    private fun init() {
        logoutPref = findPreference("logout")!!
        logoutPref.onPreferenceClickListener = this
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.pref_dialog_logout))
            .setPositiveButton(R.string.dialog_positive) { _, _ ->
                viewModel.logout()
                navigateToLogin()
            }
            .setNeutralButton(R.string.dialog_neutral, null)
            .show()
    }

    private fun navigateToLogin() {
            val destination = PreferenceFragmentDirections.actionPreferenceFragmentToLoginFragment()
            findNavController().navigate(destination)

    }
}