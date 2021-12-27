package com.yoond.iiyy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    private fun init() {
        navController = findNavController()

        binding.setLoginListener {
            navigateToHome()
        }
        binding.setGoogleListener {

        }
        binding.setSignUpListener {

        }
    }

    private fun navigateToHome() {
        val destination = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        navController.navigate(destination)
    }
}