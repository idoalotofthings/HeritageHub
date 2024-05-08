package io.github.justincodinguk.features.auth

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.ui.R
import io.github.justincodinguk.core.ui.theme.HeritageHubTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val state by viewModel.authState.collectAsState()
    var visible by remember { mutableStateOf(false) }


    val imageOffset = animateDpAsState(
        label = "Logo Animation",
        animationSpec = tween(
            durationMillis = 1000,
            easing = EaseIn
        ),
        targetValue = if(visible) 512.dp else 0.dp
    )

    val textFade = animateFloatAsState(
        label = "Title Animation",
        animationSpec = tween(
            1000,
            easing = EaseIn
        ),
        targetValue = if(visible) 1f else 0f
    )

    LaunchedEffect(Unit) {
        delay(250)
        visible = true
    }

    Scaffold(modifier=modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .offset(y = 512.dp - imageOffset.value)
                    .alpha(textFade.value)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Heritage Hub",
                modifier = Modifier
                    .offset(y = 512.dp - imageOffset.value)
                    .alpha(textFade.value),
                style = MaterialTheme.typography.displayMedium
            )

            if(textFade.value == 1f) {
                if(state.currentStatus is AuthStatus.Authenticated) {
                    navigateToHome()
                } else {
                    navigateToLogin()
                }
            }
        }
    }
}