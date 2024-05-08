package io.github.justincodinguk.features.posts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import io.github.justincodinguk.core.ui.posts.PostsList

@Composable
fun MyPostsScreen(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = hiltViewModel(),
    navigateToDetailsScreen: (String) -> Unit,
) {

    val posts by viewModel.selfAuthoredPosts.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { HeritageHubTopAppBar() }
    ) { innerPadding ->
        PostsList(
            modifier = Modifier.padding(innerPadding),
            posts = posts,
            onPostClicked = navigateToDetailsScreen
        )
    }
}