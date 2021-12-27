package com.yoond.iiyy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yoond.iiyy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        initNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_home_add || destination.id == R.id.nav_community_write) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
                binding.bottomNav.visibility = View.GONE
            } else {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
        binding.bottomNav.setupWithNavController(navController)
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setBackButtonVisible(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
        supportActionBar?.setDisplayShowHomeEnabled(visible)
    }
}