package com.yoond.iiyy.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.yoond.iiyy.MainActivity
import com.yoond.iiyy.R
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
        observeLoggedIn()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_GOOGLE_LOGIN && data != null) {
            GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener { account ->
                    viewModel.loginWithGoogle(account.idToken!!)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        context,
                        resources.getString(R.string.auth_toast_login_failed),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(TAG, "LoginWithGoogle failed", e)
                }
        }
        Log.d(TAG, requestCode.toString() + " " + resultCode.toString() + " " + (data == null))
    }

    private fun init() {
        navController = findNavController()

        binding.setLoginListener {
            login()
        }
        binding.setGoogleListener {
            loginByGoogle()
        }
        binding.setSignUpListener {
            navigateToSignup()
        }
    }

    private fun observeLoggedIn() {
        viewModel.getLoggedIn().observe(viewLifecycleOwner) { loggedIn ->
            if (loggedIn) {
                navigateToHome()
            }
        }
    }

    private fun login() {
        val email = binding.loginEmail.text.toString()
        val pwd = binding.loginPwd.text.toString()

        if (email == "") {
            Toast.makeText(context, resources.getString(R.string.login_toast_no_email), Toast.LENGTH_LONG).show()
        } else if (pwd == "") {
            Toast.makeText(context, resources.getString(R.string.login_toast_no_pwd), Toast.LENGTH_LONG).show()
        } else {
            viewModel.login(email, pwd)
            (activity as MainActivity).hideKeyboard()
        }
    }

    private fun loginByGoogle() {
        val intent = viewModel.getGoogleClient().signInIntent
        startActivityForResult(intent, CODE_GOOGLE_LOGIN)
    }

    private fun navigateToSignup() {
        val destination = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
        navController.navigate(destination)
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

    companion object {
        private const val CODE_GOOGLE_LOGIN = 9000
        private const val TAG = "LOGIN_FRAGMENT"
    }
}