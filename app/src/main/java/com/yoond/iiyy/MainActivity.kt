package com.yoond.iiyy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yoond.iiyy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        initBottomNavBar()
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.title = resources.getString(R.string.label_home)
    }

    private fun initBottomNavBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.toolbar.title = navController.currentDestination?.label
        }
        binding.bottomNav.setupWithNavController(navController)
    }
}