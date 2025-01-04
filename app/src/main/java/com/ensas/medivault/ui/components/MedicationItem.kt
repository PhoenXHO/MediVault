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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.FavoritesViewModel

// Composable to display a single medication item in a card
@Composable
fun MedicationItem(
    modifier: Modifier = Modifier,
    medication: Medication,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onItemClick: (Int) -> Unit
) {
    // State to handle loading state when adding to cart
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val medicationId = medication.id

    // Observe cart items to determine the current quantity of this medication
    val cartItems by cartViewModel.cartItems.collectAsState()
    val currentItem = cartItems.find { it.id == medication.id }
    val quantity = currentItem?.quantity ?: 0

    // Observe favorites to determine if this medication is a favorite
    val favorites by favoritesViewModel.favorites.collectAsState()
    val isFavorite = medicationId.let { favorites.contains(it) }

    // Card layout for the medication item
    Card(
        modifier = modifier
            .height(138.dp)
            .fillMaxWidth()
            .clickable { onItemClick(medication.id) }, // Navigate to details on click
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = MaterialTheme.shapes.large,
    ) {
        // Row to arrange image, details, and action buttons horizontally
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
            // Image of the medication
            ItemAsyncImage(
                modifier = Modifier.width(90.dp).height(90.dp),
                imageUrl = medication.imageUrl,
                contentDescription = medication.name,
                contentScale = ContentScale.Fit
            )

            // Column for medication name, contents, and price
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
                // Medication name with text overflow handling
                Text(
                    text = medication.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.titleMedium
                )

                // Medication contents description
                Text(
                    text = medication.contents,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.bodySmall
                )

                // Display the price of the medication
                PriceText(medication.price)
            }

            // Column for favorite button and cart actions
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = Dimensions.paddingSmall,
                        bottom = Dimensions.paddingSmall,
                        end = Dimensions.paddingSmall,
                    ),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                // Favorite button to add or remove from favorites
                FavoriteButton(
                    modifier = Modifier.size(24.dp),
                    isFavorite = isFavorite,
                    addToFavorites = { favoritesViewModel.addToFavorites(medicationId) },
                    removeFromFavorites = { favoritesViewModel.removeFromFavorites(medicationId) }
                )

                // Conditional UI: Show "Add" button or quantity chooser based on cart status
                if (quantity == 0) {
                    // Button to add medication to the cart
                    MButton(
                        modifier = Modifier
                            .width(240.dp)
                            .height(40.dp),
                        onClick = {
                            isLoading = true
                            cartViewModel.addToCart(medication)
                            isLoading = false
                            // Show a toast message upon adding to cart
                            Toast.makeText(
                                context,
                                "${medication.name} added to cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        if (isLoading) {
                            // Show loading indicator while adding to cart
                            CircularProgressIndicator()
                        } else {
                            Text(
                                text = "Add",
                                style = Typography.bodySmall
                            )
                        }
                    }
                } else {
                    // Quantity chooser to adjust the number of items in the cart
                    QuantityChooser(
                        modifier = Modifier
                            .width(240.dp)
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

@Preview
@Composable
fun MedicationItemPreview() {
    MediVaultTheme {
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
}

@Preview
@Composable
fun MedicationItemDarkPreview() {
    MediVaultTheme(darkTheme = true) {
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
}

@Preview
@Composable
fun MedicationItemAddedPreview() {
    val medication = Medication(
        id = 2, price = 10.0,
        name = "Paracetamol 500mg 20 Tablets",
        description = "This is a description of Paracetamol",
        contents = "500mg, 16 tablets",
    )
    val cartViewModel = viewModel<CartViewModel>().apply {
        addToCart(medication)
        addToCart(medication)
    }

    MediVaultTheme {
        MedicationItem(
            medication = medication,
            cartViewModel = cartViewModel,
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository()),
            onItemClick = {},
        )
    }
}

@Preview
@Composable
fun MedicationItemAddedDarkPreview() {
    val medication = Medication(
        id = 2, price = 10.0,
        name = "Paracetamol 500mg 20 Tablets",
        description = "This is a description of Paracetamol",
        contents = "500mg, 16 tablets",
    )
    val cartViewModel = viewModel<CartViewModel>().apply {
        addToCart(medication)
        addToCart(medication)
    }

    MediVaultTheme(darkTheme = true) {
        MedicationItem(
            medication = medication,
            cartViewModel = cartViewModel,
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository()),
            onItemClick = {},
        )
    }
}