package io.github.justincodinguk.core.ui.posts


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import io.github.justincodinguk.core.model.Post

@Composable
fun PostsList(
    posts: LazyPagingItems<Post>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(posts.itemCount) {
            PostCard(
                post = posts[it]!!,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}