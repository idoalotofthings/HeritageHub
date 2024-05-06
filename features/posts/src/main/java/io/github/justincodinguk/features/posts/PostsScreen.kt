package io.github.justincodinguk.features.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.justincodinguk.core.ui.navigation.HeritageHubBottomNavBar
import io.github.justincodinguk.core.ui.common.CreatePostFloatingActionButton
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import io.github.justincodinguk.core.ui.posts.PostsList


@Composable
fun PostsScreen(
    modifier: Modifier = Modifier,
    onCreatePostButtonClick: () -> Unit,
    onPostClicked: (String) -> Unit,
    viewModel: PostsViewModel = hiltViewModel()
) {

    val posts = viewModel.posts.collectAsLazyPagingItems()

    Scaffold(
        topBar = { HeritageHubTopAppBar() },
        bottomBar = { HeritageHubBottomNavBar() },
        floatingActionButton = {
            CreatePostFloatingActionButton(onClick = onCreatePostButtonClick)
        },
        modifier = modifier
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            PostsList(
                posts = posts,
                onPostClicked = onPostClicked
            )
        }
    }
}