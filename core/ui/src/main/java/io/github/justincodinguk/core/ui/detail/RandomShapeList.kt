package io.github.justincodinguk.core.ui.detail

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

fun generateRandomShapeList(radius: Int): List<Shape> {
    val shapes = listOf(
        CircleShape,
        RoundedCornerShape(radius.dp),
        RectangleShape,
        RoundedCornerShape(topStart = radius.dp, bottomEnd = radius.dp),
        RoundedCornerShape(topEnd = radius.dp, bottomStart = radius.dp)
    )

    return shapes.shuffled()
}