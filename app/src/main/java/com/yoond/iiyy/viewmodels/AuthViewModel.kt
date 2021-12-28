package com.yoond.iiyy.viewmodels

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.yoond.iiyy.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel()  {

    fun getUser() = repository.getUser()

    fun getGoogleClient() = repository.getGoogleClient()

    fun getLoggedIn() = repository.getLoggedIn()

    fun signUp(email: String, pwd: String) = repository.signUp(email, pwd)

    fun login(email: String, pwd: String) = repository.login(email, pwd)

    fun loginWithGoogle(idToken: String) = repository.loginWithGoogle(idToken)

    fun logout() = repository.logout()
}