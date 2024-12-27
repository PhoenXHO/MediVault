package com.ensas.medivault.viewmodel.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// ViewModel for managing the theme state
class ThemeViewModel : ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }
}