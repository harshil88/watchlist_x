package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.R
import com.harshilpadsala.watchlistx.data.ImageDetails

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(images: List<String>) {

    val pagerState = rememberPagerState(
        initialPage = images.size/2,
        pageCount = {
        images.size
    })


    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal =  36.dp),
        pageSpacing = 24.dp,
        pageSize = object  : PageSize{
            override fun Density.calculateMainAxisPageSize(
                availableSpace: Int,
                pageSpacing: Int
            ): Int = availableSpace
        }
    ) { page ->
        PagerContent(image = Constant.TMDB_IMAGE_URI_HIGH + images[page])

    }
}


@Composable
fun PagerContent(image: String) {
    Card(
        modifier = Modifier
            .height(184.dp)
            .fillMaxWidth()

    ) {


        AsyncImage(
            model =  ImageRequest
                .Builder(LocalContext.current)
                .data(image)
                .placeholder(R.drawable.ic_placeholder)
                .build(),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Movie Image"
        )

    }
}




