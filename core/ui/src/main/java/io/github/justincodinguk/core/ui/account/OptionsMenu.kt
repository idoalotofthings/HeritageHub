package io.github.justincodinguk.core.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.ui.R

@Composable
fun OptionsMenu(
    modifier: Modifier = Modifier,
    navigateToFollowersScreen: () -> Unit,
    navigateToMyPostsScreen: () -> Unit,
    navigateToMyHeritageScreen: () -> Unit,
    navigateToAboutScreen: () -> Unit,
    logout: () -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        OptionsElement(
            text = "Following",
            icon = painterResource(id = R.drawable.ic_following),
            onClick = navigateToFollowersScreen
        )

        OptionsElement(
            text = "My Posts",
            icon = painterResource(id = R.drawable.ic_post),
            onClick = navigateToMyPostsScreen
        )

        OptionsElement(
            text = "My Heritage",
            icon = painterResource(id = R.drawable.ic_heritage),
            onClick = navigateToMyHeritageScreen
        )

        OptionsElement(
            text = "About Heritage Hub",
            icon = painterResource(id = R.drawable.ic_info),
            onClick = navigateToAboutScreen
        )

        OptionsElement(
            text = "Logout",
            icon = painterResource(id = R.drawable.ic_logout),
            onClick = logout
        )
    }
}