package io.github.justincodinguk.core.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconText(
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(modifier) {
        if(clickable) {
            IconButton(onClick = onClick) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(2.dp)
                )
            }
        } else {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.padding(2.dp)
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
fun IconText(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if(clickable) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(2.dp)
                )
            }
        } else {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(2.dp)
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(8.dp)
        )
    }
}