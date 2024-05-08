package io.github.justincodinguk.features.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.dev.AuthStatus
import kotlinx.coroutines.delay

@Composable
fun UnverifiedUserScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit
) {

    val state by viewModel.authState.collectAsState()

    LaunchedEffect("") {
        var count = 0
        while((state.currentStatus == AuthStatus.VerificationNeeded) && count<25) {
            viewModel.checkVerificationState()
            count++
            delay(5000)
        }

        if(state.currentStatus is AuthStatus.Authenticated) {
            navigateToHome()
        } else if(count>=25){
            viewModel.deleteUser()
            navigateToLogin()
        }
    }

    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Check your inbox and follow the instructions in the mail. You'll be redirected to the home screen once verification completes",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}