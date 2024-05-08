package io.github.justincodinguk.core.ui.heritage

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.model.Heritage

@Composable
fun HeritageTree(
    modifier: Modifier = Modifier,
    heritage: Heritage
) {

    Spacer(modifier = Modifier.height(64.dp))
    LazyColumn(modifier = modifier) {
        items(heritage.heritageElements.size) {
            HeritageCard(
                heritageElement = heritage.heritageElements[it],
                showArrow = it < heritage.heritageElements.size - 1,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}