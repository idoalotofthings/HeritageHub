package io.github.justincodinguk.features.following

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import io.github.justincodinguk.core.ui.following.FollowUserInfo

@Composable
fun FollowingUsersScreen(
    modifier: Modifier = Modifier,
    viewModel: FollowingUsersViewModel = hiltViewModel()
) {

    val users by viewModel.users.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { HeritageHubTopAppBar() }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(users) {
                FollowUserInfo(
                    user = it,
                    onUnfollow = {
                        viewModel.unfollowUser(it)
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}