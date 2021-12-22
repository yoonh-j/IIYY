package com.yoond.iiyy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        initBottomNavBar()
    }

    override fun onResume() {
        super.onResume()
        binding.toolbar.title = resources.getString(R.string.title_home)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun initBottomNavBar() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.toolbar.title = navController.currentDestination?.label
        }
        binding.bottomNav.setupWithNavController(navController)
    }

    fun setBackButtonVisible(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
        supportActionBar?.setDisplayShowHomeEnabled(visible)
    }
}