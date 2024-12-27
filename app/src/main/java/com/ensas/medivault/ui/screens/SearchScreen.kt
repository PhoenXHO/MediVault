package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// For the screen that displays the search results
@Composable
fun SearchScreen(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Search Results")
    }
}

// Preview of the SearchScreen
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(navController = rememberNavController())
}