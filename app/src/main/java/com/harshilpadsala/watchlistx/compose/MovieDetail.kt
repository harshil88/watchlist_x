package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.CardListComponent
import com.harshilpadsala.watchlistx.compose.components.GenderFilterChips
import com.harshilpadsala.watchlistx.compose.components.ImagePager
import com.harshilpadsala.watchlistx.compose.components.RatingRow
import com.harshilpadsala.watchlistx.compose.components.WXToggleIconButton
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenErrorX
import com.harshilpadsala.watchlistx.compose.components.base_x.FullScreenLoaderX
import com.harshilpadsala.watchlistx.constants.FavouriteType
import com.harshilpadsala.watchlistx.data.res.detail.toRatingArgs
import com.harshilpadsala.watchlistx.data.res.model.CardModel
import com.harshilpadsala.watchlistx.data.res.model.RatingArgsModel
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.MovieDetailUiState
import com.harshilpadsala.watchlistx.vm.MovieDetailViewModel
import utils.ToastX


@Composable
fun MovieDetailRoute(
    onRatingClick: (RatingArgsModel) -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val uiState = rememberUpdatedState(newValue = viewModel.uiState)


    if(uiState.value.favMessage!=null){
        ToastX(
            message = uiState.value.favMessage
        )
        viewModel.reset()
    }

    MovieDetailScreen(
        uiState = uiState.value,
        onRatingClick = {
            onRatingClick(
                uiState.value.details?.toRatingArgs(
                    ratings = uiState.value.stats?.ratings?.value?.toInt(),
                    isRated = uiState.value.stats?.isRated?:false
                )?:RatingArgsModel()
            )
        },
        onFavouriteClick = viewModel::toggleFavourite,
        onWatchListClick = viewModel::toggleWatchList,
    )

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    uiState: MovieDetailUiState,
    onFavouriteClick: (Boolean) -> Unit,
    onWatchListClick: (Boolean) -> Unit,
    onRatingClick: () -> Unit,
) {

    if (uiState.isLoading == null) {
        FullScreenLoaderX()
    } else if (uiState.error != null) {
        FullScreenErrorX()
    } else {
        MovieDetailContent(
            uiState = uiState,
            onFavouriteClick = onFavouriteClick,
            onWatchListClick = onWatchListClick,
            onRatingClick = onRatingClick
        )
    }


}

@Composable
fun MovieDetailContent(
    uiState: MovieDetailUiState,
    onFavouriteClick: (Boolean) -> Unit,
    onWatchListClick: (Boolean) -> Unit,
    onRatingClick: () -> Unit,
) {
    LazyColumn {

        if (uiState.details?.title != null) {
            item {
                Text(
                    modifier =  Modifier.padding(start = 24.dp, bottom = 16.dp, end = 8.dp),
                    text = uiState.details?.title!!,
                    style = StylesX.titleLarge.copy(color = Darkness.rise)
                )
            }
        }

        if (uiState.images != null) {
            item {
                ImagePager(images = uiState.images!!)
            }
        }

        if (uiState.genres != null) {
            item {
                GenderFilterChips(
                    modifier = Modifier.padding(
                        vertical = 16.dp,
                    ),
                    genres = uiState.genres!!,
                    clickable = false
                )
            }
        }


        if (uiState.details != null) {
            item {
                Text(
                    text = uiState.details?.overview ?: "",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = StylesX.bodyMedium.copy(color = Darkness.grey),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                RatingRow(
                    rating = uiState.details?.voteAverage ?: 0.0,
                    users = uiState.details?.voteCount ?: 0,
                    onRatingClick = onRatingClick,
                )
            }
        }

        if (uiState.stats != null) {
            item {
                ActionButtonsRow(
                    isFavourite = uiState.isFavourite ?: false,
                    isWatchListed = uiState.isWatchList ?: false,
                    onWatchListClick = onWatchListClick,
                    onFavouriteClick = onFavouriteClick,
                )
            }
        }

        if (uiState.credits != null) {
            item {
                CreditsRow(credits = uiState.credits!!)
            }
        }

    }
}

@Composable
fun ActionButtonsRow(
    isFavourite: Boolean,
    isWatchListed: Boolean,
    onFavouriteClick: (Boolean) -> Unit,
    onWatchListClick: (Boolean) -> Unit,
) {

    val buttonModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    Spacer(modifier = Modifier.height(20.dp))

    WXToggleIconButton(modifier = buttonModifier,
        state = isFavourite,
        favouriteType = FavouriteType.Favourite,
        onClick = { onFavouriteClick(!isFavourite) })

    Spacer(modifier = Modifier.height(16.dp))

    WXToggleIconButton(modifier = buttonModifier,
        state = isWatchListed,
        favouriteType = FavouriteType.Watchlist,
        onClick = { onWatchListClick(!isWatchListed) })
    Spacer(modifier = Modifier.height(20.dp))


}


@Composable
fun CreditsRow(credits: List<CardModel>) {
    Text(
        text = "Top Casts", modifier = Modifier.padding(
            start = 24.dp, top = 24.dp, bottom = 24.dp
        ), style = StylesX.titleLarge.copy(color = Color.Yellow), maxLines = 1
    )

    CardListComponent(cards = credits, modifier = Modifier, onCardClick = { id -> })

}





