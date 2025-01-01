package com.ensas.medivault.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ensas.medivault.data.model.SearchFilter
import com.ensas.medivault.data.model.SortOption
import com.ensas.medivault.ui.theme.Dimensions

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

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Edit Filters") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Search Query
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Search Query") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                // Price Range
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = minPrice,
                        onValueChange = { minPrice = it },
                        label = { Text("Min Price") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(Dimensions.marginSmall))
                    OutlinedTextField(
                        value = maxPrice,
                        onValueChange = { maxPrice = it },
                        label = { Text("Max Price") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
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
                    sortBy = selectedSort
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

@Preview(showBackground = true)
@Composable
fun FilterDialogPreview() {
    var filter by remember { mutableStateOf(SearchFilter()) }
    FilterDialog(
        currentFilter = filter,
        onApplyFilters = { newFilter -> filter = newFilter },
        onDismiss = { }
    )
}