package com.yoond.iiyy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yoond.iiyy.databinding.ActivityMainBinding
import com.yoond.iiyy.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        initNavigation()
        checkAuth()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_login) {
                supportActionBar?.hide()
                binding.bottomNav.visibility = View.GONE
            } else if (destination.id == R.id.nav_home_add ||
                destination.id == R.id.nav_community_write) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
                binding.bottomNav.visibility = View.GONE
            } else if (destination.id == R.id.nav_community_detail) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
                binding.bottomNav.visibility = View.GONE
            } else {
                supportActionBar?.show()
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun checkAuth() {
        if (authViewModel.getLoggedIn().value != true) {
            navController.navigate(R.id.nav_login)
        }
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setBackButtonVisible(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
        supportActionBar?.setDisplayShowHomeEnabled(visible)
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}