package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// For the main scaffold component (which contains the top bar and the bottom navigation bar)
@Composable
fun MainScaffold(navController: NavController, content: @Composable () -> Unit) {
    // Scaffold is a layout component that implements the basic material design visual structure
    // It provides a top bar, a bottom navigation bar, and a floating action button
    Scaffold(
        // The top bar is a toolbar that displays the title and actions for the current screen
        topBar = { TopBar(navController) },
    ) {
        // Column is a layout component that places its children in a vertical sequence
        paddingValues -> Column(
            modifier = Modifier.padding(paddingValues),
        ) { content() }
    }
}

// Preview the MainScaffold component
@Preview(showBackground = true)
@Composable
fun MainScaffoldPreview() {
    MainScaffold(navController = rememberNavController()) {
        Text("Content - Main Scaffold")
    }
}