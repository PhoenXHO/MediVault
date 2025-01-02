package com.ensas.medivault.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ensas.medivault.data.UserInfo
import com.ensas.medivault.data.UserPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor(private val userPreferences: UserPreferences) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _currentUser = MutableStateFlow(auth.currentUser)
    open val currentUser: StateFlow<FirebaseUser?> get() = _currentUser

    // Add states for error messages
    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> get() = _authError

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser
            _currentUser.value?.let { user ->
                viewModelScope.launch {
                    userPreferences.saveUserInfo(
                        UserInfo(
                            displayName = user.displayName ?: "",
                            email = user.email ?: ""
                        )
                    )
                }
            }
        }
    }

    fun signOut() {
        auth.signOut()
        viewModelScope.launch {
            userPreferences.clearUserInfo()
        }
    }

    // Manual registration function
    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        onRegister: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = userProfileChangeRequest {
                            displayName = "$firstName $lastName"
                        }
                        user?.updateProfile(profileUpdates)
                        _authError.value = null
                        onRegister()
                    } else {
                        _authError.value = task.exception?.message
                    }
                }
            } catch (e: Exception) {
                _authError.value = e.message
            }
        }
    }

    // Manual login function
    fun login(
        email: String,
        password: String,
        onLogin: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authError.value = null
                        onLogin()
                    } else {
                        _authError.value = task.exception?.message
                    }
                }
            } catch (e: Exception) {
                _authError.value = e.message
            }
        }
    }
}