package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel

@Composable
fun CartItemRow(item: Medication, cartViewModel: CartViewModel) {
    // Row layout to display medication details and quantity controls
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Column for medication name and contents
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimensions.marginSmall)
        ) {
            Text(
                text = item.name,
                style = Typography.displaySmall
            )
            Text(
                text = item.contents,
                style = Typography.labelSmall
            )
        }
        // Display total price for the medication item
        PriceText(
            price = item.totalPrice,
            style = Typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        // Quantity chooser to adjust the number of items
        QuantityChooser(
            quantity = item.quantity,
            stylized = false,
            onIncrease = { cartViewModel.updateQuantity(item.id, item.quantity + 1) },
            onDecrease = {
                if (item.quantity > 1) {
                    // Decrease quantity if more than one
                    cartViewModel.updateQuantity(item.id, item.quantity - 1)
                } else {
                    // Remove item from cart if quantity is one
                    cartViewModel.removeFromCart(item.id)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemRowPreview() {
    MediVaultTheme {
        CartItemRow(
            item = Medication(
                id = 1,
                name = "Paracetamol",
                price = 10.0,
                quantity = 2,
                contents = "500mg, 10 tablets",
            ),
            cartViewModel = CartViewModel()
        )
    }
}