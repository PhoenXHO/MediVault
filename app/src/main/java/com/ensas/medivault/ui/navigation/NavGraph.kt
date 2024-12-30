package com.ensas.medivault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ensas.medivault.ui.components.MainScaffold
import com.ensas.medivault.ui.screens.CartScreen
import com.ensas.medivault.ui.screens.MedicationDetailsScreen
import com.ensas.medivault.ui.screens.MedicationsListScreen
import com.ensas.medivault.ui.screens.SearchScreen
import com.ensas.medivault.viewmodel.CartViewModel

@Composable
fun NavGraph(cartViewModel: CartViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Screen.MedicationsList.route) {

        composable(Screen.Search.route) {
            SearchScreen(navController, cartViewModel)
        }
        composable(Screen.Cart.route) {
            CartScreen(navController, cartViewModel)
        }
        composable(Screen.MedicationsList.route) {
            MainScaffold(navController) {
                MedicationsListScreen(navController, cartViewModel)
            }
        }
        composable(
            route = Screen.MedicationDetails.createRoute("{medicationId}"),
            arguments = listOf(navArgument("medicationId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            MainScaffold(navController) {
                MedicationDetailsScreen(
                    navController = navController,
                    medicationId = backStackEntry.arguments?.getString("medicationId"),
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavGraphPreview() {
    NavGraph()
}