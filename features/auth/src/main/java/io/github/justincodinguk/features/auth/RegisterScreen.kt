package io.github.justincodinguk.features.auth

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.ui.R
import io.github.justincodinguk.core.ui.auth.AuthConfirmButton
import io.github.justincodinguk.core.ui.auth.CredentialsTextField
import io.github.justincodinguk.core.ui.auth.ElevatedCardButton
import io.github.justincodinguk.core.ui.auth.ProfilePicturePreview
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onSignUpCompleted: () -> Unit,
    navigateToUnverifiedUserScreen: () -> Unit,
) {

    Scaffold(
        topBar = { HeritageHubTopAppBar() },
        modifier = modifier,
    ) { innerPadding ->

        val state by viewModel.authState.collectAsState()

        var verificationDialogShown by remember {
            mutableStateOf(false)
        }

        var errDialogShown by remember {
            mutableStateOf(false)
        }

        Log.d("AuthStatus", state.currentStatus.toString())
        when (state.currentStatus) {
            is AuthStatus.Unauthenticated -> RegisterScreenContent(
                state = state,
                viewModel = viewModel,
                innerPadding = innerPadding,
                onSignUp = {
                    verificationDialogShown = false
                    errDialogShown = false
                    viewModel.register()
                },
            )

            is AuthStatus.Loading -> {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator()
                }
            }

            is AuthStatus.VerificationNeeded -> navigateToUnverifiedUserScreen()

            is AuthStatus.Error -> {
                if (!errDialogShown) {
                    AlertDialog(
                        onDismissRequest = { },
                        confirmButton = {
                            TextButton(onClick = { errDialogShown = true }) {
                                Text(text = "Ok")
                            }
                        },
                        text = { Text(text = "Couldn't register this mail. Either the account already exists or there was an error") }
                    )

                }
                RegisterScreenContent(
                    state = state,
                    viewModel = viewModel,
                    innerPadding = innerPadding,
                    onSignUp = {
                        errDialogShown = false
                        verificationDialogShown = false
                        viewModel.register()
                    },
                )
            }

            is AuthStatus.Authenticated -> onSignUpCompleted()
        }
    }
}

@Composable
private fun RegisterScreenContent(
    state: AuthState,
    viewModel: AuthViewModel,
    innerPadding: PaddingValues,
    onSignUp: () -> Unit,
) {

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        if(it != null) {
            viewModel.updateText(profilePictureUri = it)
        }
    }

    Column(
        Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )

        CredentialsTextField(
            value = state.name,
            onValueChange = {
                viewModel.updateText(name = it)
            },
            label = "Display Name",
            modifier = Modifier.padding(4.dp)
        )

        CredentialsTextField(
            value = state.email,
            onValueChange = {
                viewModel.updateText(email = it)
            },
            label = "Email",
            modifier = Modifier.padding(4.dp)
        )

        CredentialsTextField(
            value = state.password,
            onValueChange = {
                viewModel.updateText(password = it)
            },
            label = "Password",
            isPassword = true,
            modifier = Modifier.padding(4.dp)
        )

        ElevatedCardButton(
            text = "Upload Profile Picture",
            icon = R.drawable.ic_upload,
            onClick = {
                galleryLauncher.launch("image/*")
            },
            modifier = Modifier.padding(4.dp)
        )

        if(state.profilePictureUri != Uri.EMPTY) {
            ProfilePicturePreview(
                uri = state.profilePictureUri,
                modifier = Modifier.padding(16.dp)
            )
        }

        AuthConfirmButton(
            text = "Sign Up", onClick = onSignUp,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 24.dp, bottom = 12.dp)
        )
    }
}