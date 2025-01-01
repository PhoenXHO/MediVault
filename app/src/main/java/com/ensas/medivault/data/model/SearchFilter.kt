package com.ensas.medivault.data.model

data class SearchFilter(
    val query: String = "",
    val minPrice: Double = 0.0,
    val maxPrice: Double = Double.MAX_VALUE,
//    val minRating: Float,
//    val maxRating: Float,
    val sortBy: SortOption = SortOption.NAME,
//    val categories: List<String>
)

enum class SortOption {
    NAME,
    PRICE_ASC,
    PRICE_DESC,
    ;

    fun getSortName(): String {
        return when (this) {
            NAME -> "Name"
            PRICE_ASC -> "Price: Low to High"
            PRICE_DESC -> "Price: High to Low"
        }
    }
}