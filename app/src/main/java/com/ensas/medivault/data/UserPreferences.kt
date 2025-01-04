package com.ensas.medivault.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property to create DataStore instance
val Context.dataStore by preferencesDataStore(name = "user_preferences")

open class UserPreferences(private val context: Context?) {
    companion object {
        // Keys for storing user information
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val PHOTO_URL = stringPreferencesKey("photo_url")
    }

    // Flow to retrieve user information from DataStore
    open val userInfo: Flow<UserInfo>? = context?.dataStore?.data?.map { preferences ->
        UserInfo(
            displayName = preferences[USERNAME] ?: "",
            email = preferences[EMAIL] ?: "",
            photoUrl = preferences[PHOTO_URL] ?: ""
        )
    }

    // Saves user information to DataStore
    open suspend fun saveUserInfo(userInfo: UserInfo) {
        context?.dataStore?.edit { preferences ->
            preferences[USERNAME] = userInfo.displayName
            preferences[EMAIL] = userInfo.email
            preferences[PHOTO_URL] = userInfo.photoUrl
        }
    }

    // Clears user information from DataStore
    open suspend fun clearUserInfo() {
        context?.dataStore?.edit { preferences ->
            preferences.clear()
        }
    }
}

// Data class representing user information
data class UserInfo(
    val displayName: String,
    val email: String,
    val photoUrl: String = ""
) {
    companion object {
        // Creates UserInfo from FirebaseUser
        fun fromFirebaseUser(user: FirebaseUser?): UserInfo {
            return UserInfo(
                displayName = user?.displayName ?: "",
                email = user?.email ?: "",
                photoUrl = user?.photoUrl.toString()
            )
        }
    }
}