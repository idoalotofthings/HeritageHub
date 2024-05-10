package io.github.justincodinguk.features.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
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
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.ui.account.AccountInfo
import io.github.justincodinguk.core.ui.account.OptionsMenu
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import io.github.justincodinguk.core.ui.navigation.HeritageHubBottomNavBar

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel(),
    navigateToFollowersScreen: () -> Unit,
    navigateToMyPostsScreen: () -> Unit,
    navigateToMyHeritageScreen: () -> Unit,
    navigateToAboutScreen: () -> Unit,
    navigateToLoginScreen: (Boolean) -> Unit,
    navigateToHome: () -> Unit
) {

    val user by viewModel.user.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { HeritageHubTopAppBar() },
        bottomBar = { HeritageHubBottomNavBar(selectedIndex = 1, navigateToHome = navigateToHome) }
    ) { innerPadding ->

        var logoutConfirmationVisible by remember {
            mutableStateOf(false)
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.padding(innerPadding).verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AccountInfo(
                user = user,
                icon = Icons.Default.Edit,
                onIconClick = {
                    // TODO: Open dialog to edit user info
                },
            )

            OptionsMenu(
                navigateToFollowersScreen = navigateToFollowersScreen,
                navigateToMyPostsScreen = navigateToMyPostsScreen,
                navigateToMyHeritageScreen = navigateToMyHeritageScreen,
                navigateToAboutScreen = navigateToAboutScreen,
                logout = { logoutConfirmationVisible = true }
            )

            if (logoutConfirmationVisible) {
                AlertDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.signOut()
                                navigateToLoginScreen(false)
                            }
                        ) {
                            Text(text = "Yes")
                        }
                    },
                    text = {
                        Text(text = "Are you sure you want to sign out?")
                    },
                    dismissButton = {
                        TextButton(onClick = { logoutConfirmationVisible = false }) {
                            Text(text = "No")
                        }
                    }
                )
            }
        }
    }
}