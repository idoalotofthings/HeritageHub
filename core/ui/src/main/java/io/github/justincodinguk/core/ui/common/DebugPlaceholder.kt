package io.github.justincodinguk.core.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun debugPlaceholder(@DrawableRes id: Int) : Painter? {
    return if(LocalInspectionMode.current) {
        painterResource(id = id)
    } else null
}