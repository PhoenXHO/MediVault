package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.data.model.SearchFilter
import com.ensas.medivault.data.model.SortOption
import com.ensas.medivault.ui.theme.Dimensions

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterBar(
    filter: SearchFilter,
    onRemoveFilter: (FilterType) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.marginMedium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.marginMedium),
        maxItemsInEachRow = Int.MAX_VALUE
    ) {
        // Query Filter
        if (filter.query.isNotEmpty()) {
            FilterChip(
                selected = true,
                onClick = { onRemoveFilter(FilterType.QUERY) },
                label = { Text("Query: ${filter.query}") },
                leadingIcon = {
                    Icon(Icons.Default.Close, "Remove Query Filter")
                },
                modifier = Modifier.height(32.dp)
            )
        }

        // Price Range Filter
        if (filter.minPrice > 0.0 || filter.maxPrice < Double.MAX_VALUE) {
            FilterChip(
                selected = true,
                onClick = { onRemoveFilter(FilterType.PRICE_RANGE) },
                label = { Text(formulatePrice(filter.minPrice, filter.maxPrice)) },
                leadingIcon = {
                    Icon(Icons.Default.Close, "Remove Price Filter")
                },
                modifier = Modifier.height(32.dp)
            )
        }

        // Only Favorites Filter
        if (filter.favoritesOnly) {
            FilterChip(
                selected = true,
                onClick = { onRemoveFilter(FilterType.FAVORITES_ONLY) },
                label = { Text("Favorites Only") },
                leadingIcon = {
                    Icon(Icons.Default.Close, "Remove Favorites Only Filter")
                },
                modifier = Modifier.height(32.dp)
            )
        }

        // Sort Option Filter
        if (filter.sortBy != SortOption.NAME) {
            FilterChip(
                selected = true,
                onClick = { onRemoveFilter(FilterType.SORT_OPTION) },
                label = { Text("Sort by ${filter.sortBy.getSortName()}") },
                leadingIcon = {
                    Icon(Icons.Default.Close, "Remove Sort Filter")
                },
                modifier = Modifier.height(32.dp)
            )
        }
    }
}

// Enum to represent filter types
enum class FilterType {
    QUERY,
    PRICE_RANGE,
    SORT_OPTION,
    FAVORITES_ONLY,
}

// Helper function to format price range
private fun formulatePrice(minPrice: Double, maxPrice: Double): String {
    return if (minPrice > 0.0 && maxPrice < Double.MAX_VALUE) {
        "Price: $minPrice - $maxPrice"
    } else if (minPrice > 0.0) {
        "Price > $minPrice"
    } else {
        "Price < $maxPrice"
    }
}

@Preview(showBackground = true)
@Composable
fun FilterBarPreview() {
    FilterBar(
        filter = SearchFilter(
            query = "Headache",
            minPrice = 10.0,
            sortBy = SortOption.PRICE_ASC,
            favoritesOnly = true
        ),
        onRemoveFilter = { /* Handle filter removal */ }
    )
}