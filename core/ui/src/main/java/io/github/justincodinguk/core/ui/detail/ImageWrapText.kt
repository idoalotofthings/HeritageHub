package io.github.justincodinguk.core.ui.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageWrapText(
    modifier: Modifier = Modifier,
    shape: Shape,
    imageUrl: String,
    text: String,
    reverseLayout: Boolean = false
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!reverseLayout) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape)
                    .padding(8.dp)
            )

            Text(
                modifier = Modifier.padding(8.dp),
                text = text
            )
        } else {

            Text(
                modifier = Modifier.padding(8.dp),
                text = text
            )

            AsyncImage(
                model = imageUrl,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape)
                    .padding(8.dp)
            )
        }
    }

}