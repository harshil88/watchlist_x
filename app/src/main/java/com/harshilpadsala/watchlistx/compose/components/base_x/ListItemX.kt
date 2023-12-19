package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import java.util.Locale


@Composable
fun ListItemX(
    mediaId : Int,
    title: String,
    voteAverage: Double,
    thumbnailPath: String,
    originalLanguage: String,
    releaseDate: String,
    modifier: Modifier = Modifier,
    onItemClick : (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {onItemClick(mediaId) }
    ) {
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImageX(
                imageSrc = thumbnailPath, modifier = modifier.width(64.dp)
            )

        }

        Column(
            modifier = modifier
                .weight(1F)
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title, style = StylesX.titleMedium.copy(color = Darkness.light), maxLines = 1
            )

            RatingRow(voteAverage = voteAverage)

            Text(
                text = "${originalLanguage.uppercase(Locale.ROOT)}  $releaseDate",
                style = StylesX.bodySmall
            )
        }
    }
}

@Composable
fun RatingRow(voteAverage: Double) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.Star,
            contentDescription = "Star Icon",
            tint = Darkness.rise,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .padding(end = 8.dp)
        )
        Text(text = voteAverage.toString(), style = StylesX.labelSmall)
    }
}