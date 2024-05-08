package io.github.justincodinguk.core.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HeritageHubBottomNavBar(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    navigateToFavorites: () -> Unit= {},
    selectedIndex: Int = 0,
    navigateToAccountScreen: () -> Unit = {}
) {

    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = selectedIndex==0,
            onClick = navigateToHome,
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = selectedIndex==1,
            onClick = navigateToAccountScreen,
            icon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Home") }
        )
    }
}