package com.yoond.iiyy.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yoond.iiyy.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val application: Application
) {
    private val auth = FirebaseAuth.getInstance()
    private val user = MutableLiveData<FirebaseUser>()
    private val loggedOut = MutableLiveData<Boolean>()

    init {
        val cur = auth.currentUser

        if (cur != null) {
            user.postValue(cur)
            loggedOut.postValue(false)
        }
    }

    fun getUser(): MutableLiveData<FirebaseUser> = user

    fun getLoggedOut(): MutableLiveData<Boolean> = loggedOut

    fun signUp(email: String, pwd: String) {
        auth.createUserWithEmailAndPassword(email, pwd)
            .addOnSuccessListener {
                user.postValue(auth.currentUser)
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    application.applicationContext,
                    application.getString(R.string.auth_toast_signup_failed),
                    Toast.LENGTH_LONG
                ).show()
                Log.e(TAG, "Sign up failed", e)
            }
    }

    fun login(email: String, pwd: String) {
        auth.signInWithEmailAndPassword(email, pwd)
            .addOnSuccessListener {
                user.postValue(auth.currentUser)
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    application.applicationContext,
                    application.getString(R.string.auth_toast_login_failed),
                    Toast.LENGTH_LONG
                ).show()
                Log.e(TAG, "Login failed", e)
            }
    }

    fun logout() {
        auth.signOut()
        loggedOut.postValue(true)
    }

    companion object {
        private const val TAG = "AUTH_REPOSITORY"
    }
}