package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel

@Composable
fun CartItemRow(item: Medication, cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
        PriceText(
            price = item.totalPrice,
            style = Typography.labelLarge
        )
        QuantityChooser(
            quantity = item.quantity,
            onIncrease = { cartViewModel.updateQuantity(item.id, item.quantity + 1) },
            onDecrease = {
                if (item.quantity > 1) {
                    cartViewModel.updateQuantity(item.id, item.quantity - 1)
                } else {
                    cartViewModel.removeFromCart(item.id)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemRowPreview() {
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