package io.github.justincodinguk.features.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
    navigateToAccount: () -> Unit,
    viewModel: PostsViewModel = hiltViewModel()
) {

    val posts = viewModel.posts.collectAsLazyPagingItems()
    val favPosts by viewModel.favPostIds.collectAsState()

    Scaffold(
        topBar = { HeritageHubTopAppBar(
            showRefreshButton = true,
            onRefresh = { posts.refresh() },
            showFavoritesButton = true,
            onFavorites = {
                viewModel.switchMode()
                posts.refresh()
            }
        ) },
        bottomBar = { HeritageHubBottomNavBar(selectedIndex = 0, navigateToAccountScreen = navigateToAccount) },
        floatingActionButton = {
            CreatePostFloatingActionButton(onClick = onCreatePostButtonClick)
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(posts.itemCount==0) {
                CircularProgressIndicator()
            } else {
                PostsList(
                    posts = posts,
                    onPostClicked = onPostClicked,
                    onPostLiked = viewModel::likePost,
                    onPostSaved = viewModel::savePost
                )
            }
        }
    }
}