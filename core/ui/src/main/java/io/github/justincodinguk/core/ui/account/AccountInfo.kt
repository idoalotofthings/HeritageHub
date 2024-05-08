package io.github.justincodinguk.core.ui.account

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.justincodinguk.core.model.User

@Composable
fun AccountInfo(
    modifier: Modifier = Modifier,
    user: User,
    icon: ImageVector,
    onIconClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = user.profileImage,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .border(
                    color = LocalContentColor.current,
                    width = 2.dp,
                    shape = CircleShape
                )
                .clip(CircleShape)


                .size(96.dp)
        )

        Text(
            text = user.name,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        IconButton(
            onClick = onIconClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = "")
        }
    }
}