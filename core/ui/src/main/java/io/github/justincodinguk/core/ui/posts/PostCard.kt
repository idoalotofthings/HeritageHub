package io.github.justincodinguk.core.ui.posts

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.justincodinguk.core.model.Heritage
import io.github.justincodinguk.core.model.Post
import io.github.justincodinguk.core.model.User
import io.github.justincodinguk.core.ui.R
import io.github.justincodinguk.core.ui.common.ReactionChip
import io.github.justincodinguk.core.ui.common.debugPlaceholder
import io.github.justincodinguk.core.ui.theme.HeritageHubTheme

@Composable
fun PostCard(
    post: Post,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onSave: () -> Unit,
    onLike: () -> Unit
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(16.dp),
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Box {
            AsyncImage(
                model = post.photoUrls[0],
                contentDescription = "Cover image",
                placeholder = debugPlaceholder(id = R.drawable.pot),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
            )

            AsyncImage(
                model = post.author.profileImage,
                contentDescription = "Owner profile",
                contentScale = ContentScale.Crop,
                placeholder = debugPlaceholder(id = R.drawable.pfp),
                modifier = Modifier
                    .offset(y = 32.dp)
                    .size(96.dp)
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .border(4.dp, MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                    .clip(CircleShape)


            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(8.dp).weight(5f)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text = "By ${post.author.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(2.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp).weight(4f)
            ) {
                Surface(
                    modifier = Modifier.padding(2.dp),
                    shape = RoundedCornerShape(32.dp),
                    color = MaterialTheme.colorScheme.inversePrimary
                ) {
                    Text(text = "Skill", modifier = Modifier.padding(8.dp))
                }

                Spacer(Modifier.height(8.dp))

                ReactionChip(
                    likeCount = 54,
                    isSaved = true,
                    isLiked = false,
                    modifier = Modifier.padding(2.dp),
                    onSave = onSave,
                    onLike = onLike
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PostCardPreview() {
    HeritageHubTheme(dynamicColor = false) {
        PostCard(
            Post(
                id = "",
                title = "The Art of Pot Making",
                author = User(
                    id = "",
                    name = "David Guetta",
                    profileImage = "",
                    heritage = Heritage()
                ),
                photoUrls = listOf(""),
                body = "",
                likeCount = 12,

            ),Modifier,{}, {}
        ) {}
    }
}