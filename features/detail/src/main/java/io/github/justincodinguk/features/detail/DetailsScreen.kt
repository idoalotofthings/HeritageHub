package io.github.justincodinguk.features.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    Scaffold(
        modifier = modifier,
        topBar = { HeritageHubTopAppBar() }
    ) { innerPadding ->

        Column(
            Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = state.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            DetailFragmentedList(
                body = state.body,
                photos = state.photoUrls,
                modifier = Modifier.fillMaxWidth(),
                author = state.author
            )
        }
    }
}