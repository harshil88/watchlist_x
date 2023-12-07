package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.harshilpadsala.watchlistx.Constant.DUMMY_IMAGE_URI
import com.harshilpadsala.watchlistx.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailTile(
    title: String?,
    posterPath: String?,
    onClick: () -> Unit,
) {

    val thumbnailCoil = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(posterPath ?: DUMMY_IMAGE_URI)
            .placeholder(R.drawable.ic_placeholder)
            .build(),
        contentScale = ContentScale.FillBounds,
        )

    Card(
            modifier = Modifier
                .height(218.dp)
                .width(128.dp),
    onClick = onClick,
    ) {
        Image(
            painter = thumbnailCoil,
            contentDescription = "This is the thumbnail content",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        )
        Text(
            text = title ?: "The Amazing Spiderman",
            modifier = Modifier.padding(16.dp),
            maxLines = 1,
        )
    }
}








