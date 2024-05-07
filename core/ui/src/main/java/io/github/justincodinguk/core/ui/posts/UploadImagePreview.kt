package io.github.justincodinguk.core.ui.posts

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.justincodinguk.core.ui.R
import io.github.justincodinguk.core.ui.common.IconText

@Composable
fun ImageUploadPreview(
    modifier: Modifier = Modifier,
    onImageUpload: () -> Unit,
    imageUris: List<Uri> = emptyList()
) {

    Column(modifier = modifier) {
        ElevatedButton(onClick = onImageUpload) {
            IconText(icon = painterResource(id = R.drawable.ic_upload), text = "Upload Images")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            for(imageUri in imageUris) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}