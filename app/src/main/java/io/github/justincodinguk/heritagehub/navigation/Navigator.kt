package io.github.justincodinguk.heritagehub.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.justincodinguk.features.auth.SplashScreen
import io.github.justincodinguk.features.account.AboutScreen
import io.github.justincodinguk.features.account.AccountScreen
import io.github.justincodinguk.features.auth.AuthViewModel
import io.github.justincodinguk.features.auth.LoginScreen
import io.github.justincodinguk.features.auth.RegisterScreen
import io.github.justincodinguk.features.auth.UnverifiedUserScreen
import io.github.justincodinguk.features.detail.DetailsScreen
import io.github.justincodinguk.features.following.FollowingUsersScreen
import io.github.justincodinguk.features.heritage.CreateHeritageScreen
import io.github.justincodinguk.features.heritage.HeritageViewModel
import io.github.justincodinguk.features.heritage.MyHeritageScreen
import io.github.justincodinguk.features.posts.MyPostsScreen
import io.github.justincodinguk.features.posts.NewPostScreen
import io.github.justincodinguk.features.posts.PostsScreen
import io.github.justincodinguk.features.posts.PostsViewModel
import io.github.justincodinguk.heritagehub.R


@Composable
fun HeritageHubNavigator(
    navController: NavHostController = rememberNavController(),
) {

    val authViewModel : AuthViewModel = hiltViewModel()
    val heritageViewModel : HeritageViewModel = hiltViewModel()
    val postsViewModel : PostsViewModel = hiltViewModel()

    NavHost(navController = navController, Routes.SPLASH.name) {
        composable(Routes.SIGN_IN.name) {
            LoginScreen(
                onSignUp = { navController.navigate(Routes.SIGN_UP.name) },
                onSignIn = { navController.navigate(Routes.HOME.name) },
                viewModel = authViewModel
            )
        }

        composable(Routes.SIGN_UP.name) {
            RegisterScreen(
                onSignUpCompleted = { navController.navigate(Routes.HOME.name) },
                navigateToUnverifiedUserScreen = {
                    Log.d("RegisterScreen", "navigateToUnverifiedUserScreen")
                    navController.navigate(Routes.UNVERIFIED_USER.name)
                },
                viewModel = authViewModel
            )
        }

        composable(Routes.HOME.name) {
            PostsScreen(
                onCreatePostButtonClick = {
                    navController.navigate(Routes.CREATE_POST.name)
                },
                onPostClicked = {
                    navController.navigate("${Routes.DETAILS.name}/${it}")
                },
                viewModel = postsViewModel,
                navigateToAccount = { navController.navigate(Routes.ACCOUNT.name) }
            )
        }

        composable("${Routes.DETAILS.name}/{postId}") {
            val postId = it.arguments?.getString("postId") ?: ""
            DetailsScreen(postId)
        }

        composable(Routes.CREATE_POST.name) {
            NewPostScreen(
                navigateBackToHomeScreen = {
                    navController.navigate(Routes.HOME.name)
                },
                viewModel = postsViewModel
            )
        }

        composable(Routes.ACCOUNT.name) {
            AccountScreen(
                navigateToFollowersScreen = { navController.navigate(Routes.FOLLOWING.name) },
                navigateToMyPostsScreen = { navController.navigate(Routes.MY_POSTS.name) },
                navigateToMyHeritageScreen = { navController.navigate("${Routes.MY_HERITAGE.name}/${authViewModel.authState.value.user?.id}") },
                navigateToAboutScreen = { navController.navigate(Routes.ABOUT.name) },
                navigateToLoginScreen = { navController.navigate(Routes.SIGN_IN.name) },
                navigateToHome = { navController.navigate(Routes.HOME.name)}
            )
        }

        composable(Routes.FOLLOWING.name) {
            FollowingUsersScreen()
        }

        composable(Routes.MY_POSTS.name) {
            MyPostsScreen(
                navigateToDetailsScreen = {
                    navController.navigate("${Routes.DETAILS.name}/${it}")
                },
                viewModel = postsViewModel
            )

        }

        composable(Routes.ABOUT.name) {
            AboutScreen()
        }

        composable(Routes.CREATE_HERITAGE.name) {
            CreateHeritageScreen()
        }

        composable("${Routes.MY_HERITAGE.name}/{id}") {
            val id = it.arguments?.getString("id") ?: ""
            MyHeritageScreen(userId = id) {
                navController.navigate(Routes.CREATE_HERITAGE.name)
            }
        }

        composable(Routes.SPLASH.name) {
            SplashScreen(
                navigateToLogin = { navController.navigate(Routes.SIGN_IN.name) },
                navigateToHome = { navController.navigate(Routes.HOME.name) }
            )
        }

        composable(Routes.UNVERIFIED_USER.name) {
            UnverifiedUserScreen(
                navigateToHome = { navController.navigate(Routes.HOME.name) },
                navigateToLogin = { navController.navigate(Routes.SIGN_IN.name) },
                viewModel = authViewModel
            )
        }
    }
}