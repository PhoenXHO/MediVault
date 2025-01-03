package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.ui.components.CartItemRow
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.components.MScaffold
import com.ensas.medivault.ui.components.PriceText
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    MScaffold(
        navController = navController,
        title = "Cart",
        backArrow = true,
        contentModifier = Modifier
            .padding(horizontal = Dimensions.paddingLarge),
        bottomBarModifier = Modifier
            .padding(Dimensions.paddingLarge)
            .padding(bottom = Dimensions.paddingMedium),
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimensions.paddingMedium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total:",
                        style = Typography.displaySmall
                    )
                    PriceText(
                        price = cartItems.sumOf { it.price * it.quantity },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                MButton(
                    onClick = { navController.navigate(Screen.Checkout.route) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = cartItems.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) { Text("Proceed to Checkout") }
            }
        }
    ) {
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your cart is empty.",
                    modifier = Modifier.padding(top = Dimensions.paddingLarge)
                )
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Review your cart items:",
                    modifier = Modifier
                        .padding(top = Dimensions.paddingLarge),
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = Dimensions.paddingSmall)
                ) {
                    items(cartItems) { item ->
                        CartItemRow(item, cartViewModel)
                        if (item != cartItems.last())
                            HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CartScreenPreview() {
    val cartViewModel = viewModel<CartViewModel>()
    cartViewModel.addToCart(Medication(
        id = 1,
        name = "Medication 1",
        contents = "Contents 1",
        price = 10.0,
        quantity = 1,
    ))
    cartViewModel.addToCart(Medication(
        id = 2,
        name = "Medication 2",
        contents = "Contents 2",
        price = 20.0,
        quantity = 2,
    ))

    MediVaultTheme {
        CartScreen(
            navController = rememberNavController(),
            cartViewModel = cartViewModel
        )
    }
}

@Preview
@Composable
fun CartScreenDarkPreview() {
    val cartViewModel = viewModel<CartViewModel>()
    cartViewModel.addToCart(Medication(
        id = 1,
        name = "Medication 1",
        contents = "Contents 1",
        price = 10.0,
        quantity = 1,
    ))
    cartViewModel.addToCart(Medication(
        id = 2,
        name = "Medication 2",
        contents = "Contents 2",
        price = 20.0,
        quantity = 2,
    ))

    MediVaultTheme(darkTheme = true) {
        CartScreen(
            navController = rememberNavController(),
            cartViewModel = cartViewModel
        )
    }
}

@Preview
@Composable
fun CartScreenEmptyPreview() {
    val cartViewModel = viewModel<CartViewModel>()
    MediVaultTheme {
        CartScreen(
            navController = rememberNavController(),
            cartViewModel = cartViewModel
        )
    }
}

@Preview
@Composable
fun CartScreenEmptyDarkPreview() {
    val cartViewModel = viewModel<CartViewModel>()
    MediVaultTheme(darkTheme = true) {
        CartScreen(
            navController = rememberNavController(),
            cartViewModel = cartViewModel
        )
    }
}