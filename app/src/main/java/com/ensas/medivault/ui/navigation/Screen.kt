package com.ensas.medivault.ui.navigation

// Sealed class representing different screens/routes in the app
sealed class Screen(val route: String) {
    // Search screen for searching medications
    data object Search : Screen("search")
    
    // Screen displaying search results
    data object SearchResults : Screen("searchResults")
    
    // Cart screen showing selected medications
    data object Cart : Screen("cart")
    
    // Home screen displaying the list of medications
    data object Home : Screen("medicationsList")
    
    // Medication details screen with a dynamic medication ID
    data object MedicationDetails : Screen("medicationDetails/{medicationId}") {
        // Creates a route with a specific medication ID
        fun createRoute(medicationId: String) = "medicationDetails/$medicationId"
    }
    
    // Checkout screen for processing purchases
    data object Checkout : Screen("checkout")
    
    // Login screen for user authentication
    data object Login : Screen("login")
    
    // Registration screen for new users
    data object Registration : Screen("registration")
    
    // Profile screen displaying user information
    data object Profile : Screen("profile")
    
    // Favorites screen listing user's favorite medications
    data object Favorites : Screen("favorites")
}