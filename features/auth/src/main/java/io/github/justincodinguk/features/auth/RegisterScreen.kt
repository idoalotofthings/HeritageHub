package io.github.justincodinguk.features.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onSignUpCompleted: () -> Unit,
) {

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Heritage Hub") }) },
        modifier = modifier,
    ) { innerPadding ->

        val state by viewModel.authState.collectAsState()

        var verificationDialogShown by remember {
            mutableStateOf(false)
        }

        var errDialogShown by remember {
            mutableStateOf(false)
        }

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

            is AuthStatus.VerificationNeeded -> {
                if (!verificationDialogShown) {
                    AlertDialog(
                        onDismissRequest = { /*TODO*/ },
                        confirmButton = { /*TODO*/ },
                        text = { Text(text = "Check your inbox and follow the instructions in the mail. You'll be redirected to the home screen once verification completes") }
                    )
                    verificationDialogShown = true
                }
            }

            is AuthStatus.Error -> {
                if (!errDialogShown) {
                    AlertDialog(
                        onDismissRequest = { /*TODO*/ },
                        confirmButton = {
                            TextButton(onClick = { errDialogShown = true }) {
                                Text(text = "Ok")
                            }
                        },
                        text = { Text(text = "Couldn't register this mail. Either the account already exists or there was an error") }
                    )
                    errDialogShown = true
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

        AuthConfirmButton(
            text = "Sign Up", onClick = {
                viewModel.register()
                onSignUp()
            },
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 24.dp, bottom = 12.dp)
        )
    }
}