package com.ensas.medivault.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.ui.components.KeyboardAwareScaffold
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.navigation.navigateTo
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    navController: NavController,
    authError: String?,
    onAuthErrorShown: () -> Unit = {},
    onRegister: (String, String, String, String) -> Unit,
    isLoading: Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(authError) {
        authError?.let {
            snackbarHostState.showSnackbar(it)
            onAuthErrorShown()
        }
    }

    KeyboardAwareScaffold(
        snackbarHostState = snackbarHostState,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Dimensions.paddingLarge),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Register", style = Typography.titleLarge)
                Spacer(modifier = Modifier.height(Dimensions.marginLarge))

                Row {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(Dimensions.marginMedium))

                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Dimensions.marginLarge))

                MButton(
                    onClick = {
                        val validationError = validateInput(
                            firstName, lastName, email, password, confirmPassword)
                        if (validationError != null) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(validationError)
                            }
                        } else {
                            onRegister(firstName, lastName, email, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading // Disable button when loading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp),
                            strokeWidth = 2.dp
                        )
                    }
                    Text("Register")
                }
                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                GoogleSignInButton(navController)
                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                Text(
                    text = "Already have an account? Login here",
                    modifier = Modifier.clickable {
                        navigateTo(navController, Screen.Login, clearStack = true)
                    },
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun validateInput(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    confirmPassword: String
): String? {
    val namePattern = "^[a-zA-Z]+\$".toRegex()
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-@\$!%*?&])[A-Za-z\\d-@\$!%*?&]{8,}\$".toRegex()

    return when {
        firstName.isBlank() ||
                lastName.isBlank() ||
                email.isBlank() ||
                password.isBlank() ||
                confirmPassword.isBlank() -> "Please fill in all fields"
        !namePattern.matches(firstName) -> "Invalid first name"
        !namePattern.matches(lastName) -> "Invalid last name"
        !emailPattern.matches(email) -> "Invalid email"
        !passwordPattern.matches(password) ->
            "Invalid password. " +
                    "Password must be at least 8 characters long, " +
                    "include uppercase and lowercase letters, " +
                    "at least one number, and one special character (-@\$!%*?&)."
        password != confirmPassword -> "Passwords do not match"
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(
        navController = rememberNavController(),
        authError = "test",
        onRegister = { _, _, _, _ -> },
        isLoading = false
    )
}