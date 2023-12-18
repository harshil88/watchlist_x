package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.R

@Composable
fun AsyncImageX(
    imageSrc : String  ,
    modifier: Modifier = Modifier,
    contentDescription : String = "Default Content Description",
    ){

    val thumbnailCoil = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageSrc)
            .placeholder(R.drawable.ic_placeholder)
            .build(),
        contentScale = ContentScale.FillBounds,
    )

    Image(
        modifier = modifier,
        painter = thumbnailCoil,
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds,
    )
}