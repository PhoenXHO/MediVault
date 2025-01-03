package com.ensas.medivault.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
import com.ensas.medivault.ui.screens.FavoritesScreen
import com.ensas.medivault.viewmodel.AuthViewModel
import com.ensas.medivault.viewmodel.CartViewModel
import com.ensas.medivault.viewmodel.FavoritesViewModel
import com.ensas.medivault.viewmodel.SearchViewModel

@Composable
fun NavGraph(
    cartViewModel: CartViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
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

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navigateTo(navController, Screen.Login, clearStack = true)
        } else {
            navigateTo(navController, Screen.Home, clearStack = true)
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                authError = authError,
                onAuthErrorShown = { authViewModel.clearAuthError() },
                onLogin = { email, password ->
                    authViewModel.login(
                        email, password,
                        onLogin = {
                            Toast.makeText(
                                navController.context,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateTo(navController, Screen.Home, clearStack = true)
                        }
                    )
                },
                isLoading = authViewModel.isLoading.collectAsState().value
            )
        }
        composable(Screen.Registration.route) {
            RegistrationScreen(
                navController = navController,
                authError = authError,
                onAuthErrorShown = { authViewModel.clearAuthError() },
                onRegister = { firstName, lastName, email, password ->
                    authViewModel.register(
                        firstName, lastName, email, password,
                        onRegister = {
                            Toast.makeText(
                                navController.context,
                                "Registration successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateTo(navController, Screen.Home, clearStack = true)
                        }
                    )
                },
                isLoading = authViewModel.isLoading.collectAsState().value
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
                viewModel = searchViewModel,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(navController, cartViewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(
            route = Screen.MedicationDetails.createRoute("{medicationId}"),
            arguments = listOf(navArgument("medicationId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val medicationId = backStackEntry.arguments?.getInt("medicationId")!!
            MedicationDetailsScreen(
                navController = navController,
                medicationId = medicationId,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel
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
                    Toast.makeText(
                        navController.context,
                        "Logged out successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateTo(navController, Screen.Login, clearStack = true)
                }
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel
            )
        }
    }
}

fun navigateTo(navController: NavController, screen: Screen, clearStack: Boolean = false) {
    navController.navigate(screen.route) {
        if (clearStack) {
            popUpTo(0) { // Clear the back stack
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}