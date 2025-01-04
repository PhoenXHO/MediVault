package com.ensas.medivault.data.model

// Data class to represent the search filter criteria
data class SearchFilter(
    val query: String = "", // Search query string
    val minPrice: Double = 0.0, // Minimum price filter
    val maxPrice: Double = Double.MAX_VALUE, // Maximum price filter
    val sortBy: SortOption = SortOption.NAME, // Sort option
    val favoritesOnly: Boolean = false // Flag to show only favorite medications
)

// Enum class to represent different sorting options
enum class SortOption {
    NAME, // Sort by medication name
    PRICE_ASC, // Sort by price in ascending order
    PRICE_DESC, // Sort by price in descending order
    ;

    // Function to get the display name for each sort option
    fun getSortName(): String {
        return when (this) {
            NAME -> "Name"
            PRICE_ASC -> "Price: Low to High"
            PRICE_DESC -> "Price: High to Low"
        }
    }
}