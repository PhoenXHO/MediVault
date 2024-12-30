package com.ensas.medivault.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.components.QuantityChooser
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.MedicationDetailsViewModel

@Composable
fun MedicationDetailsScreen(navController: NavController,
                            medicationId: String?,
                            cartViewModel: CartViewModel = hiltViewModel(),
                            viewModel: MedicationDetailsViewModel = hiltViewModel()) {
    medicationId?.let {
        viewModel.fetchMedicationDetails(it)
    }

    val medication by viewModel.medication.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val currentItem = cartItems.find { it.id == medicationId }
    val quantity = currentItem?.quantity ?: 0

    medication?.let { med ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.paddingMedium)
        ) {
            Text(text = med.name, style = Typography.titleMedium)
            Spacer(modifier = Modifier.height(Dimensions.marginExtraLarge))

            Text(text = med.description)
            Spacer(modifier = Modifier.height(Dimensions.marginLarge))

            Text(text = "Dosage: ${med.contents}")
            Spacer(modifier = Modifier.height(Dimensions.marginLarge))

            Text(text = "Manufacturer: ${med.manufacturer}")
            Spacer(modifier = Modifier.height(Dimensions.marginLarge))

            Text(text = "Usage Instructions: ${med.usageInstructions}")
            Spacer(modifier = Modifier.height(Dimensions.marginLarge))

            Text(text = "Price: ${med.price} MAD", style = Typography.titleSmall)
            Spacer(modifier = Modifier.height(Dimensions.marginExtraLarge))

            // Conditional UI for Add to cart or Quantity Chooser
            if (quantity == 0) {
                MButton(
                    onClick = {
                        cartViewModel.addToCart(med)
                        Toast.makeText(
                            navController.context,
                            "Added to cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) { Text("Add to cart") }
            } else {
                QuantityChooser(
                    quantity = quantity,
                    onIncrease = { cartViewModel.updateQuantity(med.id, quantity + 1) },
                    onDecrease = {
                        if (quantity > 1) {
                            cartViewModel.updateQuantity(med.id, quantity - 1)
                        } else {
                            cartViewModel.removeFromCart(med.id)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } ?: run {
        Text("Loading...")
    }
}

// Preview of the MedicationDetailsScreen
@Preview(showBackground = true)
@Composable
fun MedicationDetailsScreenPreview() {
    MedicationDetailsScreen(
        navController = rememberNavController(),
        cartViewModel = CartViewModel(),
        medicationId = "1"
    )
}