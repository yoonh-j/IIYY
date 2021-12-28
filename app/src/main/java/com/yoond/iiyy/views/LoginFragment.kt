package com.yoond.iiyy.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.databinding.FragmentLoginBinding
import com.yoond.iiyy.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackPressed()
    }

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
            login()
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

    private fun setBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(this) {
            activity?.finish()
        }?.isEnabled
    }

    private fun login() {
        val email = binding.loginId.text.toString()
        val pwd = binding.loginPwd.text.toString()

        viewModel.login(email, pwd)
        navigateToHome()
        (activity as MainActivity).hideKeyboard()
    }
}