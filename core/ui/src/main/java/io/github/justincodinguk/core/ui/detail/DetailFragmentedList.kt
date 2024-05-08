package io.github.justincodinguk.core.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.model.User

@Composable
fun DetailFragmentedList(
    modifier: Modifier = Modifier,
    body: String,
    author: User,
    photos: List<String>,
    shapes: List<Shape> = generateRandomShapeList(64)
) {

    val splitBody = body.split(".")

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
            Text(text = "By", modifier.padding(16.dp), fontWeight = FontWeight.Bold)
            AuthorInfo(
                user = author,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}