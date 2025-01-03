package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.data.model.SearchFilter
import com.ensas.medivault.data.model.SortOption
import com.ensas.medivault.data.repository.FakeFavoritesRepository
import com.ensas.medivault.data.repository.FakeRepository
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.components.MScaffold
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Typography
import com.ensas.medivault.viewmodel.SearchViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    var minPrice by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("") }
    var selectedSort by remember { mutableStateOf(SortOption.NAME) }
    var favoritesOnly by remember { mutableStateOf(false) }

    MScaffold(
        navController = navController,
        title = "Search",
        backArrow = true,
        contentModifier = Modifier.padding(horizontal = Dimensions.paddingLarge),
        bottomBarModifier = Modifier
            .padding(Dimensions.paddingLarge)
            .padding(bottom = Dimensions.paddingMedium),
        bottomBar = {
            // Search button
            MButton(
                onClick = {
                    viewModel.setFilters(
                        SearchFilter(
                            query = query,
                            minPrice = minPrice.toDoubleOrNull() ?: 0.0,
                            maxPrice = maxPrice.toDoubleOrNull() ?: Double.MAX_VALUE,
                            sortBy = selectedSort,
                            favoritesOnly = favoritesOnly
                        )
                    )
                    navController.navigate(Screen.SearchResults.route)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                content = { Text("Search") },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search medications") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(Dimensions.marginMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = minPrice,
                    onValueChange = { minPrice = it },
                    label = { Text("Min price") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(Dimensions.marginMedium))
                OutlinedTextField(
                    value = maxPrice,
                    onValueChange = { maxPrice = it },
                    label = { Text("Max price") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.padding(Dimensions.marginMedium))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = favoritesOnly,
                    onCheckedChange = { isChecked ->
                        favoritesOnly = isChecked
                    }
                )
                Text(text = "Favorites Only")
            }

            Spacer(modifier = Modifier.padding(Dimensions.marginMedium))

            Text("Sort by:", style = Typography.titleSmall)
            Spacer(modifier = Modifier.padding(Dimensions.marginSmall))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement
                    .spacedBy(Dimensions.marginMedium,
                        Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(Dimensions.marginMedium),
            ) {
                SortOption.entries.forEach { option ->
                    FilterChip(
                        selected = selectedSort == option,
                        onClick = { selectedSort = option },
                        label = { Text(option.getSortName()) },
                        modifier = Modifier.height(32.dp)
                    )
                }
            }
        }
    }
}

// Preview of the SearchScreen
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        navController = rememberNavController(),
        viewModel = SearchViewModel(
            FakeRepository(),
            FakeFavoritesRepository()
        )
    )
}