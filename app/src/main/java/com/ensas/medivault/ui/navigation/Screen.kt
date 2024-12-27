package com.ensas.medivault.ui.navigation

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object Cart : Screen("cart")
    object MedicationsList : Screen("medicationsList")
    object MedicationDetails : Screen("medicationDetails/{medicationId}") {
        fun createRoute(medicationId: String) = "medicationDetails/$medicationId"
    }
}