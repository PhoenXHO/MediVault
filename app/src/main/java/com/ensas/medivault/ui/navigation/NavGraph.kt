package com.ensas.medivault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ensas.medivault.ui.components.MainScaffold
import com.ensas.medivault.ui.screens.CartScreen
import com.ensas.medivault.ui.screens.MedicationDetailsScreen
import com.ensas.medivault.ui.screens.MedicationsListScreen
import com.ensas.medivault.ui.screens.SearchScreen

// For the navigation graph, which defines the navigation paths in the app
@Composable
fun NavGraph() {
    // `rememberNavController` is used to create a NavController that will manage the navigation
    val navController = rememberNavController()
    // The NavHost composable is used to define the navigation paths in the app
    NavHost(navController = navController,
        startDestination = Screen.MedicationsList.route) {

        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
        composable(Screen.Cart.route) {
            CartScreen(navController)
        }
        composable(Screen.MedicationsList.route) {
            MainScaffold(navController) {
                MedicationsListScreen(navController)
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
                    medicationId = backStackEntry.arguments?.getString("medicationId")
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