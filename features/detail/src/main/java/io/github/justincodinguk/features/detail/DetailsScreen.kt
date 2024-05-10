package io.github.justincodinguk.features.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import io.github.justincodinguk.core.ui.detail.AuthorInfo
import io.github.justincodinguk.core.ui.detail.DetailFragmentedList

@Composable
fun DetailsScreen(
    postId: String,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    viewModel.loadPost(postId)
    val state by viewModel.post.collectAsState()

    var followVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = { HeritageHubTopAppBar(
            showFavoritesButton = true,
            onFavorites = { viewModel.savePost() }
        ) }
    ) { innerPadding ->

        Column(
            Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = state.post.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            if(state.post.body.isNotEmpty()) {
                DetailFragmentedList(
                    body = state.post.body,
                    photos = state.post.photoUrls,
                    modifier = Modifier.fillMaxWidth(),
                    author = state.post.author,
                    onSave = viewModel::savePost,
                    isAuthorFollowed = state.isAuthorFollowed,
                    onFollow = {
                        followVisible = true
                        viewModel.followUser()
                    }
                )
            } else {
                CircularProgressIndicator()
            }

            if (followVisible) {
                AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = {
                    TextButton(onClick = { followVisible = false }) {
                        Text(text = "Ok")
                    }
                },
                    text = { Text(text = "You will now receive notifications from ${state.post.author.name}") })
            }
        }
    }
}