package io.github.justincodinguk.core.ui.posts

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.ui.common.IconText
import io.github.justincodinguk.core.ui.theme.HeritageHubTheme

@Composable
fun ReactionChip(
    likeCount: Int,
    isSaved: Boolean,
    isLiked: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier,
        color = MaterialTheme.colorScheme.inversePrimary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            IconText(
                icon = if (isLiked) {
                    Icons.Filled.ThumbUp
                } else {
                    Icons.Outlined.ThumbUp
                },
                text = likeCount.toString(),
                modifier = Modifier.padding(horizontal = 4.dp),
                clickable = true,
                onClick = { /*TODO*/ }
            )

            VerticalDivider(
                Modifier.height(32.dp).padding(horizontal=4.dp),
                color = LocalContentColor.current
            )

            IconButton(onClick = { /*TODO*/ }) {
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReactionChipPreview() {
    HeritageHubTheme {
        ReactionChip(likeCount = 54, isSaved = true, isLiked = false)
    }
}