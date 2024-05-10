package io.github.justincodinguk.core.ui.following

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.justincodinguk.core.model.User

@Composable
fun FollowUserInfo(
    modifier: Modifier = Modifier,
    user: User,
    onUnfollow: () -> Unit,
    onClick: (String) -> Unit
) {
    Card(modifier = modifier.clickable { onClick(user.id) }) {
        Row {
            AsyncImage(
                model = user.profileImage,
                contentDescription = "Pfp",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .size(48.dp)
            )

            Column(Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = user.name,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.fillMaxWidth())

                Button(onClick = onUnfollow) {
                    Text(text = "Unfollow")
                }
            }
        }
    }
}