package com.ensas.medivault.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ensas.medivault.data.model.Medication
import com.ensas.medivault.data.repository.FakeFavoritesRepository
import com.ensas.medivault.ui.screens.FavoriteButton
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.FavoritesViewModel

// To define the layout of a single medication item
@Composable
fun MedicationItem(
    medication: Medication,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onItemClick: (Int) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val medicationId = medication.id

    // Retrieve the current quantity from CartViewModel
    val cartItems by cartViewModel.cartItems.collectAsState()
    val currentItem = cartItems.find { it.id == medication.id }
    val quantity = currentItem?.quantity ?: 0

    val favorites by favoritesViewModel.favorites.collectAsState()
    val isFavorite = medicationId.let { favorites.contains(it) }

    // Display the medication item in a Card
    Card(
        modifier = Modifier
            .height(138.dp)
			.fillMaxWidth()
            .clickable { onItemClick(medication.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = Dimensions.paddingLarge,
                    top = Dimensions.paddingMedium,
                    bottom = Dimensions.paddingMedium,
                    end = Dimensions.paddingMedium
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Medication image
            ItemAsyncImage(
                modifier = Modifier.width(90.dp).height(90.dp),
                imageUrl = medication.imageUrl,
                contentDescription = medication.name,
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(148.dp)
                    .padding(
                        start = Dimensions.paddingLarge,
                        top = Dimensions.paddingLarge,
                        bottom = Dimensions.paddingLarge,
                        end = Dimensions.paddingMedium
                    ),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                // Medication name
                Text(
                    text = medication.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.titleMedium
                )

                // Medication info
                Text(
                    text = medication.contents,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.bodySmall
                )

                // Medication price
                PriceText(medication.price)
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                // Heart icon for favorites
                FavoriteButton(
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = (-8).dp, y = 8.dp),
                    isFavorite = isFavorite,
                    addToFavorites = { favoritesViewModel.addToFavorites(medicationId) },
                    removeFromFavorites = { favoritesViewModel.removeFromFavorites(medicationId) }
                )

                // Conditional UI for Add to cart or Quantity Chooser
                if (quantity == 0) {
                    MButton(
                        modifier = Modifier
                            .width(200.dp)
                            .height(40.dp)
                            .padding(Dimensions.paddingSmall),
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
                                text = "Add",
                                style = Typography.labelSmall
                            )
                        }
                    }
                } else {
                    QuantityChooser(
                        modifier = Modifier
                            .width(200.dp)
                            .height(40.dp),
                        quantity = quantity,
                        onIncrease = { cartViewModel.updateQuantity(medication.id, quantity + 1) },
                        onDecrease = {
                            if (quantity > 1) {
                                cartViewModel.updateQuantity(medication.id, quantity - 1)
                            } else {
                                cartViewModel.removeFromCart(medication.id)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicationItemPreview() {
    MedicationItem(
        medication = Medication(
            id = 1, price = 10.0,
            name = "Paracetamol",
            description = "This is a description of Paracetamol",
            contents = "500mg, 16 tablets",
        ),
        cartViewModel = viewModel(),
        favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository()),
        onItemClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun MedicationItemAddedPreview() {
    val medication = Medication(
        id = 3, price = 10.0,
        name = "Paracetamol 500mg 20 Tablets",
        description = "This is a description of Paracetamol",
        contents = "500mg, 16 tablets",
    )
    val cartViewModel = viewModel<CartViewModel>().apply {
        addToCart(medication)
        addToCart(medication)
    }

    MedicationItem(
        medication = medication,
        cartViewModel = cartViewModel,
        favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository()),
        onItemClick = {},
    )
}