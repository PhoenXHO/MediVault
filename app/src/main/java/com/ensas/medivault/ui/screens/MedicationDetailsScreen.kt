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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.data.repository.FakeRepository
import com.ensas.medivault.ui.components.BackButton
import com.ensas.medivault.ui.components.ItemAsyncImage
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.components.MainScaffold
import com.ensas.medivault.ui.components.PriceText
import com.ensas.medivault.ui.components.QuantityChooser
import com.ensas.medivault.ui.components.SectionTitle
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.MedicationDetailsViewModel

@Composable
fun MedicationDetailsScreen(
    navController: NavController,
    medicationId: String?,
    cartViewModel: CartViewModel = hiltViewModel(),
    viewModel: MedicationDetailsViewModel = hiltViewModel()
) {
    medicationId?.let {
        viewModel.fetchMedicationDetails(it)
    }

    val medication by viewModel.medication.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val currentItem = cartItems.find { it.id == medicationId }
    val quantity = currentItem?.quantity ?: 0

    medication?.let { med ->
        MainScaffold(
            navController = navController,
            contentModifier = Modifier.padding(horizontal = Dimensions.paddingLarge),
            bottomBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .padding(Dimensions.paddingLarge)
                        .padding(bottom = Dimensions.paddingMedium),
                    tonalElevation = 0.dp
                ) {
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
            }
        ) {
            BackButton(navController)
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
                    Text(text = med.description)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Important Information", icon = Icons.Outlined.Info)
                    Text(text = "Do not take this medication if you are allergic to any of its ingredients")
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Before Use", icon = Icons.Rounded.WarningAmber)
                    Text(text = "Consult your doctor before using this medication")
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Usage Instructions", icon = Icons.Rounded.MedicalServices)
                    Text(text = med.usageInstructions)
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Dosage", icon = Icons.Rounded.Schedule)
                    Text(text = "Always take the prescribed dosage")
                    Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                    SectionTitle(title = "Side Effects", icon = Icons.Rounded.Dangerous)
                    Text(text = "Side effects may include drowsiness, nausea, and headache")
                }
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
        medicationId = "1",
        viewModel = MedicationDetailsViewModel(FakeRepository())
    )
}