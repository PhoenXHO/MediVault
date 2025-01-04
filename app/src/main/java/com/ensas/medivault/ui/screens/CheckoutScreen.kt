package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.components.MScaffold
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.viewmodel.CartViewModel

@Composable
fun CheckoutScreen(navController: NavController, cartViewModel: CartViewModel) {
    // Main scaffold for the checkout screen
    MScaffold(
        navController = navController,
        title = "Checkout",
        backArrow = true,
        contentModifier = Modifier.padding(horizontal = Dimensions.paddingLarge),
    ) {
        // Column to layout checkout information and confirmation button
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Instruction text for payment
            Text("Proceed with your payment details.")
            Spacer(modifier = Modifier.height(20.dp))
            
            // Confirm purchase button to finalize the order
            MButton(
                onClick = { /* Handle checkout action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text("Confirm Purchase")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    MediVaultTheme {
        CheckoutScreen(
            navController = rememberNavController(),
            cartViewModel = CartViewModel()
        )
    }
}