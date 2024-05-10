package io.github.justincodinguk.core.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import io.github.justincodinguk.core.model.User
import io.github.justincodinguk.core.ui.R


@Composable
fun AuthorInfo(
    user: User,
    modifier: Modifier = Modifier,
    isFollowed: Boolean = false,
    onFollow: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = user.profileImage,
                contentDescription = "Owner pfp",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(8.dp)
                    .size(96.dp)
            )

            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )

            IconButton(
                onClick = onFollow,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    painter = if(isFollowed) painterResource(id = R.drawable.ic_notif_active) else painterResource(id = R.drawable.ic_notif),
                    contentDescription = ""
                )
            }
        }
    }
}