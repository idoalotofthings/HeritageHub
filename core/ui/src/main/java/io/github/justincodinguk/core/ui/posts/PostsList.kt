package io.github.justincodinguk.core.ui.posts


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import io.github.justincodinguk.core.model.Post


@Composable
fun PostsList(
    posts: LazyPagingItems<Post>,
    modifier: Modifier = Modifier,
    onPostClicked: (String) -> Unit,
    onPostLiked: (String) -> Unit,
    onPostSaved: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(posts.itemCount) {
            PostCard(
                post = posts[it]!!,
                modifier = Modifier.padding(4.dp),
                onClick = {
                    onPostClicked(posts[it]!!.id)
                },
                onSave = { onPostSaved(posts[it]!!.id) },
                onLike = { onPostLiked(posts[it]!!.id) }
            )
        }
    }
}

@Composable
fun PostsList(
    posts: List<Post>,
    modifier: Modifier = Modifier,
    onPostClicked: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(posts) {
            PostCard(
                post = it,
                modifier = Modifier.padding(4.dp),
                onClick = {
                    onPostClicked(it.id)
                },{},{}
            )
        }
    }
}
