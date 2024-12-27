package com.ensas.medivault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ensas.medivault.ui.navigation.NavGraph
import com.ensas.medivault.ui.theme.MediVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediVaultTheme {
                NavGraph()
            }
        }
    }
}