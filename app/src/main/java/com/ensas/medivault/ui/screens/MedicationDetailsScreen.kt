package com.ensas.medivault.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Dangerous
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.MedicalServices
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.data.repository.FakeFavoritesRepository
import com.ensas.medivault.data.repository.FakeRepository
import com.ensas.medivault.ui.components.ItemAsyncImage
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.components.MScaffold
import com.ensas.medivault.ui.components.PriceText
import com.ensas.medivault.ui.components.QuantityChooser
import com.ensas.medivault.ui.components.SectionTitle
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.FavoritesViewModel
import com.ensas.medivault.viewmodel.MedicationDetailsViewModel
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun MedicationDetailsScreen(
    navController: NavController,
    medicationId: Int,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    viewModel: MedicationDetailsViewModel = hiltViewModel()
) {
    medicationId.let {
        viewModel.fetchMedicationDetails(it)
    }

    val medication by viewModel.medication.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val currentItem = cartItems.find { it.id == medicationId }
    val quantity = currentItem?.quantity ?: 0

    val favorites by favoritesViewModel.favorites.collectAsState()
    val isFavorite = medicationId.let { favorites.contains(it) }

    medication?.let { med ->
        MScaffold(
            navController = navController,
            title = "Medication Details",
            backArrow = true,
            contentModifier = Modifier.padding(Dimensions.paddingLarge),
            actions = {
                FavoriteButton(
                    isFavorite = isFavorite,
                    addToFavorites = { favoritesViewModel.addToFavorites(medicationId) },
                    removeFromFavorites = { favoritesViewModel.removeFromFavorites(medicationId) }
                )
            },
            bottomBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .padding(horizontal = Dimensions.paddingLarge)
                        .padding(bottom = Dimensions.paddingLarge)
                        .padding(bottom = Dimensions.paddingMedium),
                    tonalElevation = 0.dp,
                    color = Color.Transparent,
                ) {
                    // Conditional UI for Add to cart or Quantity Chooser
                    if (quantity == 0) {
                        MButton(
                            modifier = Modifier.height(40.dp),
                            onClick = {
                                cartViewModel.addToCart(med)
                                Toast.makeText(
                                    navController.context,
                                    "Added to cart",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) { Text("Add to cart") }
                    } else {
                        QuantityChooser(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            quantity = quantity,
                            onIncrease = { cartViewModel.updateQuantity(med.id, quantity + 1) },
                            onDecrease = {
                                if (quantity > 1) {
                                    cartViewModel.updateQuantity(med.id, quantity - 1)
                                } else {
                                    cartViewModel.removeFromCart(med.id)
                                }
                            },
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Fixed header
                Column(modifier = Modifier.fillMaxWidth()) {
                    ItemAsyncImage(
                        imageUrl = med.imageUrl,
                        contentDescription = med.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = Dimensions.paddingLarge),
                        contentScale = ContentScale.Fit
                    )

                    Text(text = med.name, style = Typography.titleLarge)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))
                    Text(text = med.contents)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))
                    PriceText(price = med.price, style = Typography.displayLarge)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    SectionTitle(title = "Description", icon = Icons.Rounded.Description)
                    MarkdownText(med.description)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Important Information", icon = Icons.Outlined.Info)
                    MarkdownText(med.importantInfo)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Before Use", icon = Icons.Rounded.WarningAmber)
                    MarkdownText(med.precautions)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Uses", icon = Icons.Rounded.MedicalServices)
                    MarkdownText(med.uses)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Dosage", icon = Icons.Rounded.Schedule)
                    MarkdownText(med.dosage)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Side Effects", icon = Icons.Rounded.Dangerous)
                    MarkdownText(med.sideEffects)

                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
                }
            }
        }
    } ?: run {
        Text("Loading...")
    }
}

@Preview
@Composable
fun MedicationDetailsScreenPreview() {
    MediVaultTheme {
        MedicationDetailsScreen(
            navController = rememberNavController(),
            cartViewModel = CartViewModel(),
            medicationId = 0,
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository()),
            viewModel = MedicationDetailsViewModel(FakeRepository())
        )
    }
}

@Preview
@Composable
fun MedicationDetailsScreenDarkPreview() {
    MediVaultTheme(darkTheme = true) {
        MedicationDetailsScreen(
            navController = rememberNavController(),
            cartViewModel = CartViewModel(),
            medicationId = 0,
            favoritesViewModel = FavoritesViewModel(FakeFavoritesRepository()),
            viewModel = MedicationDetailsViewModel(FakeRepository())
        )
    }
}