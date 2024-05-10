package io.github.justincodinguk.core.ui.heritage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.model.HeritageElement
import io.github.justincodinguk.core.ui.R
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment

@Composable
fun HeritageCard(
    modifier: Modifier = Modifier,
    heritageElement: HeritageElement,
    showArrow: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = heritageElement.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )

                HorizontalDivider(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp))

                Text(
                    text = heritageElement.generation,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )
            }
        }
        if(showArrow) {
            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                for(i in (0..1)) {
                    Image(
                        painter = painterResource(id = R.drawable.vine_connector),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .size(64.dp),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}