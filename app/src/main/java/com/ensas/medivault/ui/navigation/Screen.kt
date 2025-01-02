package com.ensas.medivault.ui.navigation

sealed class Screen(val route: String) {
    data object Search : Screen("search")
    data object SearchResults : Screen("searchResults")
    data object Cart : Screen("cart")
    data object Home : Screen("medicationsList")
    data object MedicationDetails : Screen("medicationDetails/{medicationId}") {
        fun createRoute(medicationId: String) = "medicationDetails/$medicationId"
    }
    data object Checkout : Screen("checkout")
    data object Login : Screen("login")
    data object Registration : Screen("registration")
    data object Profile : Screen("profile")
}