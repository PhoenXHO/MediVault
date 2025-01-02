package com.ensas.medivault.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ensas.medivault.data.UserInfo
import com.ensas.medivault.ui.screens.CheckoutScreen
import com.ensas.medivault.ui.screens.CartScreen
import com.ensas.medivault.ui.screens.LoginScreen
import com.ensas.medivault.ui.screens.MedicationDetailsScreen
import com.ensas.medivault.ui.screens.HomeScreen
import com.ensas.medivault.ui.screens.RegistrationScreen
import com.ensas.medivault.ui.screens.SearchResultsScreen
import com.ensas.medivault.ui.screens.SearchScreen
import com.ensas.medivault.ui.screens.ProfileScreen
import com.ensas.medivault.viewmodel.AuthViewModel
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.SearchViewModel

@Composable
fun NavGraph(
    cartViewModel: CartViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val currentUser by authViewModel.currentUser.collectAsState()
    val authError by authViewModel.authError.collectAsState()

    val startDestination = if (currentUser == null) {
        Screen.Login.route
    } else {
        Screen.Home.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                authError = authError,
                onLogin = { email, password ->
                    authViewModel.login(
                        email, password,
                        onLogin = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                            Toast.makeText(
                                navController.context,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            )
        }
        composable(Screen.Registration.route) {
            RegistrationScreen(
                navController = navController,
                authError = authError,
                onRegister = { firstName, lastName, email, password ->
                    authViewModel.register(
                        firstName, lastName, email, password,
                        onRegister = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Registration.route) { inclusive = true }
                            }
                            Toast.makeText(
                                navController.context,
                                "Registration successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            )
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
        composable(Screen.Home.route) {
            HomeScreen(navController, cartViewModel)
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
        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                currentUser = UserInfo.fromFirebaseUser(currentUser),
                onLogout = {
                    authViewModel.signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }
}