package com.ensas.medivault

import android.app.Application
import android.os.StrictMode
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MediVaultApplication : Application() {
    override fun onCreate() {
        // Enable StrictMode policies in debug builds for debugging purposes
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
        }

        super.onCreate()
        // Initialize Firebase when the application starts
        FirebaseApp.initializeApp(this)
    }
}