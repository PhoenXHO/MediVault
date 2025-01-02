package com.ensas.medivault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ensas.medivault.ui.navigation.NavGraph
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            FirebaseApp.initializeApp(this@MainActivity)
        }

        enableEdgeToEdge()
        setContent {
            MediVaultTheme {
                NavGraph()
            }
        }
    }
}