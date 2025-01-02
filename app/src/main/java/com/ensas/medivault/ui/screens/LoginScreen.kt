package com.ensas.medivault.ui.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.auth.GoogleAuthUiClient
import com.ensas.medivault.data.UserInfo
import com.ensas.medivault.data.UserPreferences
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    authError: String?,
    onLogin: (String, String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(authError) {
        authError?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Dimensions.paddingLarge),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Login",
                    style = Typography.titleLarge)
                Spacer(modifier = Modifier.height(Dimensions.marginLarge))

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
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(Dimensions.marginLarge))

                MButton(
                    onClick = {
                        val error = validateInput(email, password)
                        if (error != null) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(error)
                            }
                        } else {
                            onLogin(email, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                GoogleSignInButton(navController)
                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                Text(
                    text = "Don't have an account? Register here",
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Registration.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

private fun validateInput(
    email: String,
    password: String
): String? {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    return when {
        email.isBlank() || password.isBlank() -> "Please fill in all fields"
        !emailPattern.matches(email) -> "Invalid email address"
        else -> null
    }
}

@Composable
fun GoogleSignInButton(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        GoogleAuthUiClient.signInWithGoogle(
            context, scope, null,
            login = { user ->
                scope.launch {
                    userPreferences.saveUserInfo(
                        UserInfo(
                            displayName = user.displayName ?: "",
                            email = user.email ?: ""
                        )
                    )
                }
                navController.navigate(Screen.Home.route)
                Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
            }
        )
    }

    MButton(
        onClick = {
            GoogleAuthUiClient.signInWithGoogle(
                context, scope, launcher,
                login = { user ->
                    scope.launch {
                        userPreferences.saveUserInfo(
                            UserInfo(
                                displayName = user.displayName ?: "",
                                email = user.email ?: ""
                            )
                        )
                    }
                    navController.navigate(Screen.Home.route)
                    Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
                }
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Sign in with Google")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        authError = null,
        onLogin = { _, _ -> }
    )
}