package com.yoond.iiyy.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
import com.yoond.iiyy.databinding.FragmentSignupBinding
import com.yoond.iiyy.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        init()
        observeLoggedIn()

        return binding.root
    }

    private fun init() {
        binding.setConfirmListener {
            val email = binding.signupEmail.text.toString()
            val pwd = binding.signupPwd.text.toString()
            val pwdCheck = binding.signupPwdCheck.text.toString()

            if (email == "") {
                Toast.makeText(context, resources.getString(R.string.login_toast_no_email), Toast.LENGTH_LONG).show()
            } else if (pwd == "") {
                Toast.makeText(context, resources.getString(R.string.login_toast_no_pwd), Toast.LENGTH_LONG).show()
            } else if (pwd.length < 6) {
                Toast.makeText(context, resources.getString(R.string.signup_toast_too_short), Toast.LENGTH_LONG).show()
            } else if (pwd != pwdCheck) {
                Toast.makeText(context, resources.getString(R.string.signup_toast_no_match), Toast.LENGTH_LONG).show()
            } else {
                viewModel.signUp(email, pwd)
                (activity as MainActivity).hideKeyboard()
            }
        }
        binding.setCancelListener {
            navigateUp()
        }
    }

    private fun observeLoggedIn() {
        viewModel.getLoggedIn().observe(viewLifecycleOwner) { loggedIn ->
            if (loggedIn) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        val destination = SignupFragmentDirections.actionSignupFragmentToHomeFragment()
        findNavController().navigate(destination)
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }
}