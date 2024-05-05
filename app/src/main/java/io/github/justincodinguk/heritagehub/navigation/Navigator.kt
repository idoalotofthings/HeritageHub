package io.github.justincodinguk.heritagehub.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.justincodinguk.features.auth.LoginScreen
import io.github.justincodinguk.features.auth.RegisterScreen
import io.github.justincodinguk.features.posts.PostsScreen


@Composable
fun HeritageHubNavigator(
    navController: NavHostController = rememberNavController(),
) {
    // TODO: Replace Sign in screen with a splash screen
    NavHost(navController = navController, Routes.SIGN_IN.name) {
        composable(Routes.SIGN_IN.name) {
            LoginScreen(
                onSignUp = { navController.navigate(Routes.SIGN_UP.name) },
                onGoogleSignIn = { /*TODO*/ },
                onSignIn = { navController.navigate(Routes.HOME.name) }
            )
        }

        composable(Routes.SIGN_UP.name) {
            RegisterScreen(
                onSignUpCompleted = { navController.navigate(Routes.HOME.name) },
            )
        }

        composable(Routes.HOME.name) {
            PostsScreen()
        }
    }
}