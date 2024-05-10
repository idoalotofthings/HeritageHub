package io.github.justincodinguk.heritagehub

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import io.github.justincodinguk.core.ui.theme.HeritageHubTheme
import io.github.justincodinguk.heritagehub.navigation.HeritageHubNavigator

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            Log.d("TOKEN", task.result)

        }
        setContent {
            HeritageHubTheme(dynamicColor = false) {
                HeritageHubNavigator()
            }
        }
    }
}