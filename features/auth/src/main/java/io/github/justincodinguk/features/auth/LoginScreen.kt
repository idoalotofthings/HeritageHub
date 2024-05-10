package io.github.justincodinguk.features.auth


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.ui.auth.AuthConfirmButton
import io.github.justincodinguk.core.ui.auth.CredentialsTextField
import io.github.justincodinguk.core.ui.auth.ElevatedCardButton
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onSignUp: () -> Unit,
    onSignIn: () -> Unit
) {

    val state by viewModel.authState.collectAsState()
    var errorDialogShown by remember {
        mutableStateOf(false)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { }

    Scaffold(
        topBar = { HeritageHubTopAppBar() },
        modifier = modifier,
    ) { innerPadding ->

        when (state.currentStatus) {
            is AuthStatus.VerificationNeeded -> {}
            is AuthStatus.Loading -> {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(LocalContext.current, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                    CircularProgressIndicator()
                }
            }

            is AuthStatus.Authenticated -> onSignIn()

            is AuthStatus.Error -> {
                if (!errorDialogShown) {
                    AlertDialog(
                        onDismissRequest = {},
                        confirmButton = {
                            Button(onClick = { errorDialogShown = true }) {
                                Text(text = "Ok")
                            }
                        },
                        text = { Text(text = "Couldn't sign you in. Incorrect credentials or bad network") }
                    )
                }
                AuthScreenContent(
                    state = state,
                    viewModel = viewModel,
                    innerPadding = innerPadding,
                    onSignIn = { errorDialogShown = false; viewModel.login() },
                    onSignUp = onSignUp
                )

            }

            AuthStatus.Unauthenticated -> AuthScreenContent(
                state = state,
                viewModel = viewModel,
                innerPadding = innerPadding,
                onSignIn = {
                    errorDialogShown = false; viewModel.login()
                },
                onSignUp = onSignUp
            )
        }
    }
}

@Composable
private fun AuthScreenContent(
    state: AuthState,
    viewModel: AuthViewModel,
    innerPadding: PaddingValues,
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
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

        AuthConfirmButton(
            text = "Sign In", onClick = onSignIn,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 24.dp, bottom = 12.dp)
        )
/*
        ElevatedCardButton(
            text = "Sign in with Google",
            icon = io.github.justincodinguk.core.ui.R.drawable.google,
            onClick = {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(true)
                    .setServerClientId(BuildConfig.SERVER_CLIENT_ID)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                coroutineScope.launch {

                        val credentialManager = androidx.credentials.CredentialManager.create(context)
                        val result = credentialManager.getCredential(
                            request = request,
                            context = context
                        )

                        if(result.credential is CustomCredential) {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(result.credential.data)
                            viewModel.signInWithGoogle(
                                accountId = googleIdTokenCredential.idToken,
                                name = googleIdTokenCredential.displayName ?: "",
                                mail = googleIdTokenCredential.id,
                                profilePictureUri = googleIdTokenCredential.profilePictureUri ?: Uri.EMPTY
                            )
                        }
                }
            },
            modifier = Modifier.padding(horizontal = 32.dp)
        )
*/
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            HorizontalDivider(Modifier.width(48.dp))
            Text(text = "OR", modifier = Modifier.padding(horizontal = 4.dp))
            HorizontalDivider(Modifier.width(48.dp))
        }

        AuthConfirmButton(
            text = "Sign Up",
            modifier = Modifier.padding(horizontal = 32.dp),
            onSignUp
        )
    }
}