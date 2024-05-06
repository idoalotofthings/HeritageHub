package io.github.justincodinguk.core.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun DetailFragmentedList(
    modifier: Modifier = Modifier,
    body: String,
    photos: List<String>,
    shapes: List<Shape> = generateRandomShapeList(32)
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
                reverseLayout = reverse
            )
            reverse = !reverse
        }
    }
}