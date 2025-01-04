package com.ensas.medivault.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ensas.medivault.data.UserInfo
import com.ensas.medivault.ui.components.MBottomBar
import com.ensas.medivault.ui.components.MButton
import com.ensas.medivault.ui.components.MainScaffold
import com.ensas.medivault.ui.navigation.Screen
import com.ensas.medivault.ui.theme.Dimensions
import com.ensas.medivault.ui.theme.MediVaultTheme

@Composable
fun ProfileScreen(
    navController: NavController,
    currentUser: UserInfo?,
    showConfirmationDialog: Boolean = false,
    onLogout: () -> Unit
) {
    // State variable to control the visibility of the logout confirmation dialog
    var showDialog by remember { mutableStateOf(showConfirmationDialog) }

    MainScaffold(
        navController = navController,
        title = "Profile",
        contentModifier = Modifier.padding(horizontal = Dimensions.paddingExtraLarge),
        bottomBar = {
            MBottomBar(
                navController = navController,
                currentScreen = Screen.Profile,
                modifier = Modifier
                    .padding(horizontal = Dimensions.paddingMedium)
                    .padding(bottom = Dimensions.paddingLarge)
                    .padding(bottom = Dimensions.paddingSmall),
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            currentUser?.let { user ->
                // Display the user's profile photo
                SubcomposeAsyncImage(
                    contentDescription = "Profile Photo",
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            user.photoUrl.ifEmpty { "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png" }
                        )
                        .crossfade(true)
                        .build(),
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    loading = {
                        // Show a loading indicator while the image is loading
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        // Show a fallback UI in case the image fails to load
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Display the user's name
                Text(
                    text = user.displayName,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display the user's email
                Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(24.dp))

                // Logout button to initiate the logout process
                MButton(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("Logout")
                }
            } ?: run {
                // Display a message if no user information is available
                Text("No user information available.")
            }
        }
    }

    if (showDialog) {
        // Confirmation dialog for logging out
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                // Confirm logout action
                MButton(
                    onClick = {
                        onLogout()
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) { Text("Logout") }
            },
            dismissButton = {
                // Cancel logout action
                MButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    MediVaultTheme {
        ProfileScreen(
            navController = rememberNavController(),
            currentUser = UserInfo(
                displayName = "John Doe",
                email = "john.doe@example.com",
            ),
            onLogout = {}
        )
    }
}

@Preview
@Composable
fun ProfileScreenDarkPreview() {
    MediVaultTheme(darkTheme = true) {
        ProfileScreen(
            navController = rememberNavController(),
            currentUser = UserInfo(
                displayName = "John Doe",
                email = "john.doe@example.com",
            ),
            onLogout = {}
        )
    }
}

@Preview
@Composable
fun ProfileScreenShowDialogPreview() {
    MediVaultTheme {
        ProfileScreen(
            navController = rememberNavController(),
            currentUser = UserInfo(
                displayName = "John Doe",
                email = "john.doe@example.com",
            ),
            showConfirmationDialog = true,
            onLogout = {}
        )
    }
}

@Preview
@Composable
fun ProfileScreenShowDialogDarkPreview() {
    MediVaultTheme(darkTheme = true) {
        ProfileScreen(
            navController = rememberNavController(),
            currentUser = UserInfo(
                displayName = "John Doe",
                email = "john.doe@example.com",
            ),
            showConfirmationDialog = true,
            onLogout = {}
        )
    }
}