package com.harshilpadsala.watchlistx.compose.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.R
import com.harshilpadsala.watchlistx.data.res.detail.CardModel
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import utils.PaddingX


//todo : Remove padding at the end of the card list
//todo : NUNI -> Remove Corner Splashes Using

@Composable
fun CardListComponent(
    cards: List<CardModel>,
    modifier: Modifier = Modifier,
    onCardClick: (CardModel) -> Unit,
    onLongCardClick: (CardModel) -> Unit = {},
    ) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = cards.size,
        ) { index ->
            PaddingX(
                start = if (index == 0) 16.dp else 0.dp
            ) {
                CardDetail(
                    title = cards[index].title,
                    posterPath = cards[index].imageUri,
                    onClick = { onCardClick(cards[index]) },
                    onLongClick = { onLongCardClick(cards[index]) }
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardDetail(
    title: String?,
    posterPath: String?,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    ) {

    val thumbnailCoil = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterPath ?: Constant.DUMMY_IMAGE_URI).placeholder(R.drawable.ic_placeholder)
            .build(),
        contentScale = ContentScale.FillBounds,
    )
    Card(
        modifier = Modifier
            .height(218.dp)
            .width(128.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick =onLongClick,
            )
    ) {
        Image(
            modifier = Modifier
                .weight(0.5F)
                .fillMaxWidth(),
            painter = thumbnailCoil,
            contentDescription = "This is the thumbnail content",
            contentScale = ContentScale.FillBounds,
        )

        Text(
            text = title ?: "",
            modifier = Modifier.padding(8.dp),
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = StylesX.labelMedium
        )
    }


}