package io.github.justincodinguk.core.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.model.User

@Composable
fun DetailFragmentedList(
    modifier: Modifier = Modifier,
    body: String,
    author: User,
    isAuthorFollowed: Boolean = false,
    onFollow: () ->Unit = {},
    onSave: () -> Unit = {},
    photos: List<String>,
    shapes: List<Shape> = generateRandomShapeList(64)
) {

    val splitBody = body.split(".")
    var isSaved by remember { mutableStateOf(false) }

    LazyColumn(modifier = modifier) {
        var reverse = false
        items(photos.size) { i ->
            ImageWrapText(
                shape = shapes[i],
                imageUrl = photos[i],
                text = splitBody[i],
                modifier = Modifier.padding(8.dp),
                reverseLayout = i%2==0
            )
            reverse = !reverse
        }

        item {
            Row {
                Column {
                    Text(text = "By", modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                    AuthorInfo(
                        user = author,
                        modifier = Modifier.padding(16.dp),
                        isAuthorFollowed,
                        onFollow
                    )
                }

                IconButton(onClick = { isSaved = !isSaved; onSave() }) {
                    Icon(
                        imageVector = if (isSaved) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        tint = if (isSaved) {
                            Color.Red
                        } else {
                            LocalContentColor.current
                        },
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}