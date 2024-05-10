package io.github.justincodinguk.core.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.ui.R
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.outlined.FavoriteBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeritageHubTopAppBar(
    modifier: Modifier = Modifier,
    showRefreshButton: Boolean = false,
    showFavoritesButton: Boolean = false,
    onRefresh: () -> Unit = {},
    onFavorites: () -> Unit = {}
) {

    var favsActive by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 16.dp).size(32.dp)
                )
                Text(text = "Heritage Hub")
            }
        },
        modifier = modifier,
        actions = {
            if (showRefreshButton) {
                IconButton(onClick = onRefresh) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "")
                }
            }
            if (showFavoritesButton) {
                IconButton(onClick = { onFavorites(); favsActive = !favsActive }) {
                    Icon(
                        imageVector = if (favsActive) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = ""
                    )
                }
            }
        },
    )
}