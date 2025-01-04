package com.ensas.medivault.auth

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.ensas.medivault.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient {
    companion object {
        // Initiates sign-in with Google and handles the credential retrieval process
        fun signInWithGoogle(
            context: Context,
            scope: CoroutineScope,
            launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
            login: (user: FirebaseUser) -> Unit
        ) {
            // Initialize CredentialManager for handling credentials
            val credentialManager = CredentialManager.create(context)
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getCredentialOptions(context))
                .build()

            scope.launch {
                try {
                    // Attempt to retrieve credentials
                    val result = credentialManager.getCredential(context, request)
                    when (val credential = result.credential) {
                        is CustomCredential -> {
                            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                // Extract ID token from Google credentials
                                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                                val idToken = googleIdTokenCredential.idToken

                                // Proceed with Firebase authentication using the ID token
                                firebaseSignInWithGoogle(context, idToken, login)
                            } else {
                                Log.e("GoogleAuthUiClient", "Unsupported CustomCredential type")
                            }
                        }
                        else -> {
                            Log.e("GoogleAuthUiClient", "Unsupported credential type: ${credential.type}")
                        }
                    }
                } catch (e: NoCredentialException) {
                    // Handle case where no credentials are found
                    // Launch intent to add a Google account if no credentials are found
                    launcher?.launch(getAddGoogleAccountIntent())
                } catch (e: GetCredentialException) {
                    // Handle credential retrieval failure
                    e.printStackTrace()
                }
            }
        }

        // Handles Firebase sign-in with the obtained Google ID token
        private suspend fun firebaseSignInWithGoogle(
            context: Context,
            idToken: String,
            login: (user: FirebaseUser) -> Unit
        ) {
            if (idToken.isEmpty()) {
                Log.e("GoogleAuthUiClient", "ID token is null or empty")
                return
            }

            try {
                val authCredential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = Firebase.auth.signInWithCredential(authCredential).await()
                val user = authResult.user
                if (user != null && user.isAnonymous.not()) {
                    // Save user information to Firestore
                    saveUserToFirestore(user)
                    login.invoke(user)
                } else {
                    Log.e("GoogleAuthUiClient", "Authentication failed: User is null or anonymous")
                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("GoogleAuthUiClient", "Firebase sign-in failed", e)
                Toast.makeText(context, "Authentication Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Saves authenticated user information to Firestore
        private fun saveUserToFirestore(user: FirebaseUser) {
            val db = FirebaseFirestore.getInstance()
            val userData = hashMapOf(
                "uid" to user.uid,
                "email" to user.email,
                "name" to user.displayName,
                "photoUrl" to user.photoUrl.toString()
            )

            db.collection("users").document(user.uid)
                .set(userData)
                .addOnSuccessListener {
                    Log.d("GoogleAuthUiClient", "DocumentSnapshot added with ID: ${user.uid}")
                }
                .addOnFailureListener { e ->
                    Log.e("GoogleAuthUiClient", "Error adding document", e)
                }
        }

        // Configures credential options for Google ID tokens
        private fun getCredentialOptions(context: Context): CredentialOption {
            return GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .build()
        }

        // Creates an intent to add a Google account
        private fun getAddGoogleAccountIntent(): Intent {
            return Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
            }
        }
    }
}