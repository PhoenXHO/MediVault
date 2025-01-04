package com.ensas.medivault.viewmodel

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
class AuthViewModel @Inject constructor(private val userPreferences: UserPreferences) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // StateFlow to hold the current authenticated user
    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> get() = _currentUser

    // StateFlow to hold authentication error messages
    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> get() = _authError

    // StateFlow to indicate loading state during authentication processes
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        // Listen for authentication state changes
        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser
            _currentUser.value?.let { user ->
                viewModelScope.launch {
                    // Save user information to preferences upon successful authentication
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

    // Function to sign out the current user
    fun signOut() {
        auth.signOut()
        viewModelScope.launch {
            // Clear user information from preferences upon sign-out
            userPreferences.clearUserInfo()
        }
    }

    // Function to handle user registration with email and password
    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        onRegister: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Create a new user with email and password
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        // Update the user's profile with display name
                        val profileUpdates = userProfileChangeRequest {
                            displayName = "$firstName $lastName"
                        }
                        user?.updateProfile(profileUpdates)
                        _authError.value = null
                        onRegister()
                    } else {
                        // Set authentication error message if registration fails
                        _authError.value = task.exception?.message
                    }
                }.addOnCompleteListener {
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // Handle exceptions and set error message
                _authError.value = e.message
                _isLoading.value = false
            }
        }
    }

    // Function to handle user login with email and password
    fun login(
        email: String,
        password: String,
        onLogin: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Sign in the user with email and password
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authError.value = null
                        onLogin()
                    } else {
                        // Set authentication error message if login fails
                        _authError.value = task.exception?.message
                    }
                }.addOnCompleteListener {
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // Handle exceptions and set error message
                _authError.value = e.message
                _isLoading.value = false
            }
        }
    }

    // Function to clear authentication error messages
    fun clearAuthError() {
        _authError.value = null
    }
}