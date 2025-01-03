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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ensas.medivault.R
import com.ensas.medivault.auth.GoogleAuthUiClient
import com.ensas.medivault.data.UserInfo
import com.ensas.medivault.data.UserPreferences
import com.ensas.medivault.ui.components.KeyboardAwareScaffold
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.components.MTextField
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.navigation.navigateTo
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme
import com.ensas.medivault.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    authError: String?,
    onAuthErrorShown: () -> Unit = {},
    onLogin: (String, String) -> Unit,
    isLoading: Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Login",
                    style = Typography.titleLarge)
                Spacer(modifier = Modifier.height(Dimensions.marginExtraLarge))

                MTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                MTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Password",
                    modifier = Modifier.fillMaxWidth(),
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(Dimensions.marginLarge))

                MButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading, // Disable button when loading
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
                    Text("Login")
                }

                GoogleSignInButton(navController)
                Spacer(modifier = Modifier.height(Dimensions.marginMedium))

                Text(
                    text = "Don't have an account? Register here",
                    modifier = Modifier.clickable {
                        navigateTo(navController, Screen.Registration, clearStack = true)
                    },
                    color = MaterialTheme.colorScheme.secondary,
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
                Toast.makeText(context, "Log in successful", Toast.LENGTH_SHORT)
                    .show()
            }
        )
    }

    MButton(
        modifier = Modifier.fillMaxWidth(),
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
                    Toast.makeText(context, "Log in successful", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_google),
            contentDescription = "Google",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.scrim
        )
        Spacer(modifier = Modifier.width(Dimensions.marginMedium))
        Text(
            text = "Sign in with Google",
            color = MaterialTheme.colorScheme.scrim
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    MediVaultTheme {
        LoginScreen(
            navController = rememberNavController(),
            authError = null,
            onLogin = { _, _ -> },
            isLoading = false
        )
    }
}

@Preview
@Composable
fun LoginScreenDarkPreview() {
    MediVaultTheme(darkTheme = true) {
        LoginScreen(
            navController = rememberNavController(),
            authError = null,
            onLogin = { _, _ -> },
            isLoading = false
        )
    }
}