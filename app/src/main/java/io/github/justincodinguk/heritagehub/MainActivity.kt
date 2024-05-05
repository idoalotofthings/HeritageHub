package io.github.justincodinguk.heritagehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import io.github.justincodinguk.core.ui.theme.HeritageHubTheme
import io.github.justincodinguk.features.auth.LoginScreen
import io.github.justincodinguk.features.posts.PostsScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeritageHubTheme(dynamicColor = false) {
                LoginScreen()
            }
        }
    }
}

