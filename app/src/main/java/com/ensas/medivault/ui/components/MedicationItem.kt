package com.ensas.medivault.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel

// To define the layout of a single medication item
@Composable
fun MedicationItem(medication: Medication,
                   cartViewModel: CartViewModel,
                   onItemClick: (String) -> Unit) {
    // `mutableStateOf` is used to create a state that can be updated
    // This state is used to show a loading indicator when the "Add to cart" button is clicked
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(medication.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Medication image
            ItemAsyncImage(
                imageUrl = medication.imageUrl,
                contentDescription = medication.name
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Medication info
            Text(text = medication.name)
            PriceText(medication.price)

            Spacer(modifier = Modifier.height(8.dp))

            // Add to cart button
            MButton(
                onClick = {
                    isLoading = true
                    cartViewModel.addToCart(medication)
                    isLoading = false
                    Toast.makeText(
                        context,
                        "${medication.name} added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = "Add to cart",
                        style = Typography.bodySmall
                    )
                }
            }
        }
    }
}

// Preview of the MedicationItem
@Preview(showBackground = true)
@Composable
fun MedicationItemPreview() {
    MedicationItem(
        medication = Medication(
            "123",
            "Paracetamol",
            "This is a description of Paracetamol",
            10.0,
            "https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/android/android.png"),
        cartViewModel = CartViewModel(),
        onItemClick = {}
    )
}