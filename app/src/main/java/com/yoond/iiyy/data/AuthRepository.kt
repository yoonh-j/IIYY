package com.yoond.iiyy.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.yoond.iiyy.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val application: Application
) {
    private val auth = FirebaseAuth.getInstance()
    private val user = MutableLiveData<FirebaseUser>()
    private val loggedIn = MutableLiveData<Boolean>()
    private lateinit var googleClient: GoogleSignInClient

    init {
        val cur = auth.currentUser

        if (cur != null) {
            user.postValue(cur)
            loggedIn.postValue(true)
        }
    }

    fun getUser(): MutableLiveData<FirebaseUser> = user

    fun getGoogleClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(application.baseContext, gso)

        return googleClient
    }

    fun getLoggedIn(): MutableLiveData<Boolean> = loggedIn

    fun signUp(email: String, pwd: String) {
        auth.createUserWithEmailAndPassword(email, pwd)
            .addOnSuccessListener {
                user.postValue(auth.currentUser)
                loggedIn.postValue(true)
            }
            .addOnFailureListener { e ->
                user.postValue(null)
                loggedIn.postValue(false)
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
                loggedIn.postValue(true)
            }
            .addOnFailureListener { e ->
                user.postValue(null)
                loggedIn.postValue(false)
                Toast.makeText(
                    application.applicationContext,
                    application.getString(R.string.auth_toast_login_failed),
                    Toast.LENGTH_LONG
                ).show()
                Log.e(TAG, "Login failed", e)
            }
    }

    fun loginWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                user.postValue(auth.currentUser)
                loggedIn.postValue(true)
            }
            .addOnFailureListener { e ->
                user.postValue(null)
                loggedIn.postValue(false)
                Toast.makeText(
                    application.applicationContext,
                    application.getString(R.string.auth_toast_login_failed),
                    Toast.LENGTH_LONG
                ).show()
                Log.e(TAG, "LoginWithGoogle failed", e)
            }
    }

    fun logout() {
        auth.signOut()
        user.postValue(null)
        loggedIn.postValue(false)
    }

    companion object {
        private const val TAG = "AUTH_REPOSITORY"
    }
}