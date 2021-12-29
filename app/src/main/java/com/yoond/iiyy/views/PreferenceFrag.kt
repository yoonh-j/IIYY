package com.yoond.iiyy.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference

import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferenceFrag : PreferenceFragmentCompat() {
    private lateinit var logoutPref: Preference
    private lateinit var licensePref: Preference
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        init()
    }

    private fun init() {
        logoutPref = findPreference("logout")!!
        logoutPref.setOnPreferenceClickListener {
            showDialog()
            true
        }

        licensePref = findPreference("license")!!
        licensePref.setOnPreferenceClickListener {
            startLicenseActivity()
            true
        }
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

    private fun startLicenseActivity() {
        val intent = Intent(context, OssLicensesMenuActivity::class.java)
        startActivity(intent)
        OssLicensesMenuActivity.setActivityTitle(resources.getString(R.string.pref_etc_license))
    }
}