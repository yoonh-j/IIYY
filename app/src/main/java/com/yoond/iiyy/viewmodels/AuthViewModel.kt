package com.yoond.iiyy.viewmodels

import androidx.lifecycle.ViewModel
import com.yoond.iiyy.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel()  {

    fun getUser() = repository.getUser()

    fun getLoggedOut() = repository.getLoggedOut()

    fun signUp(email: String, pwd: String) = repository.signUp(email, pwd)

    fun login(email: String, pwd: String) = repository.login(email, pwd)

    fun logout() = repository.logout()
}