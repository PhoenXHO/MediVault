package com.ensas.medivault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ensas.medivault.ui.screens.CheckoutScreen
import com.ensas.medivault.ui.screens.CartScreen
import com.ensas.medivault.ui.screens.LoginScreen
import com.ensas.medivault.ui.screens.MedicationDetailsScreen
import com.ensas.medivault.ui.screens.MedicationsListScreen
import com.ensas.medivault.ui.screens.RegistrationScreen
import com.ensas.medivault.ui.screens.SearchResultsScreen
import com.ensas.medivault.ui.screens.SearchScreen
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.SearchViewModel

@Composable
fun NavGraph(cartViewModel: CartViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val searchViewModel = hiltViewModel<SearchViewModel>()

    NavHost(navController = navController,
        startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Registration.route) {
            RegistrationScreen(navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(
                navController,
                searchViewModel
            )
        }
        composable(Screen.SearchResults.route) {
            SearchResultsScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                viewModel = searchViewModel
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(navController, cartViewModel)
        }
        composable(Screen.MedicationsList.route) {
            MedicationsListScreen(navController, cartViewModel)
        }
        composable(
            route = Screen.MedicationDetails.createRoute("{medicationId}"),
            arguments = listOf(navArgument("medicationId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            MedicationDetailsScreen(
                navController = navController,
                medicationId = backStackEntry.arguments?.getString("medicationId"),
                cartViewModel = cartViewModel
            )
        }
        composable(Screen.Checkout.route) {
            CheckoutScreen(navController, cartViewModel)
        }
    }
}