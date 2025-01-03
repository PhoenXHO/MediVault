package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.data.model.SearchFilter
import com.ensas.medivault.data.model.SortOption
import com.ensas.medivault.ui.theme.CustomShapes
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme

@Composable
fun FilterDialog(
    currentFilter: SearchFilter,
    onApplyFilters: (SearchFilter) -> Unit,
    onDismiss: () -> Unit
) {
    var query by remember { mutableStateOf(currentFilter.query) }
    var minPrice by remember { mutableStateOf(currentFilter.minPrice.toString()) }
    var maxPrice by remember { mutableStateOf(currentFilter.maxPrice.takeIf { it != Double.MAX_VALUE }?.toString() ?: "") }
    var selectedSort by remember { mutableStateOf(currentFilter.sortBy) }
    var favoritesOnly by remember { mutableStateOf(currentFilter.favoritesOnly) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Edit Filters") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Search Query
                MTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = "Search query",
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                )

                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                // Price Range
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MTextField(
                        value = minPrice,
                        onValueChange = { minPrice = it },
                        placeholder = "Min price",
                        modifier = Modifier.weight(1f),
                        shape = CustomShapes.LeftRoundedCornerShape,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Spacer(modifier = Modifier.width(Dimensions.marginMedium))
                    MTextField(
                        value = maxPrice,
                        onValueChange = { maxPrice = it },
                        placeholder = "Max price",
                        modifier = Modifier.weight(1f),
                        shape = CustomShapes.RightRoundedCornerShape,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                // Only Favorites Filter
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = favoritesOnly,
                        onCheckedChange = { favoritesOnly = it },
                    )
                    Text(text = "Favorites Only")
                }

                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                // Sort Options
                Text("Sort By:", style = MaterialTheme.typography.titleMedium)
                Column {
                    SortOption.entries.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = selectedSort == option,
                                onClick = { selectedSort = option }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option.getSortName())
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedFilter = SearchFilter(
                    query = query,
                    minPrice = minPrice.toDoubleOrNull() ?: 0.0,
                    maxPrice = maxPrice.toDoubleOrNull() ?: Double.MAX_VALUE,
                    sortBy = selectedSort,
                    favoritesOnly = favoritesOnly
                )
                onApplyFilters(updatedFilter)
                onDismiss()
            }) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun FilterDialogPreview() {
    var filter by remember { mutableStateOf(SearchFilter()) }
    MediVaultTheme {
        FilterDialog(
            currentFilter = filter,
            onApplyFilters = { newFilter -> filter = newFilter },
            onDismiss = { }
        )
    }
}

@Preview
@Composable
fun FilterDialogDarkPreview() {
    var filter by remember { mutableStateOf(SearchFilter()) }
    MediVaultTheme(darkTheme = true) {
        FilterDialog(
            currentFilter = filter,
            onApplyFilters = { newFilter -> filter = newFilter },
            onDismiss = { }
        )
    }
}