package com.ensas.medivault.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.navigation.Screen

// For the top bar component (which contains the search bar and the cart button)
@OptIn(ExperimentalMaterial3Api::class) // This annotation is used to indicate that the following Composable function is experimental
@Composable
fun TopBar(navController: NavController) {
    // TopAppBar is a toolbar that displays the title and actions for the current screen
    TopAppBar(
        // The title is the text that appears in the center of the top bar
        title = { Text("MediVault") },
        // The actions are the buttons that appear on the right side of the top bar
        actions = {
            IconButton(onClick = { navController.navigate(Screen.Search.route) }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Cart")
            }
        }
    )
}

// Preview the TopBar component
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(navController = rememberNavController())
}