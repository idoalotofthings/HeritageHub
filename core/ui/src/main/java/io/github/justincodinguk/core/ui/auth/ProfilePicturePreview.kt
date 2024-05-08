package io.github.justincodinguk.core.ui.auth

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfilePicturePreview(
    modifier: Modifier = Modifier,
    uri: Uri
) {
    AsyncImage(
        model = uri,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
    )
}