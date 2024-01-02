package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.compose.components.TopBarX
import com.harshilpadsala.watchlistx.compose.components.WXButton
import com.harshilpadsala.watchlistx.compose.components.base_x.AsyncImageX
import com.harshilpadsala.watchlistx.state.RatingUiState
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.RatingViewModel
import utils.ToastX

private const val RATINGS_DEFAULT_VALUE = 9

@Composable
fun RatingRoute(
    onBackPress: () -> Unit,
    viewModel: RatingViewModel = hiltViewModel(),
) {

    val uiState = rememberUpdatedState(newValue = viewModel.uiState)

    val ratingState = remember {
        mutableIntStateOf(uiState.value.currentRating ?: RATINGS_DEFAULT_VALUE)
    }

    if (uiState.value.failure != null) {
        ToastX(message = uiState.value.failure!!)
    }

    if (uiState.value.success != null) {
        onBackPress()
    }

    RatingScreen(
        ratingUiState = uiState.value,
        ratingState = ratingState,
        onSubmitRating = viewModel::submitRatings,
        onDeleteRating = viewModel::deleteRatings,
        onCancelPress = onBackPress,
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RatingScreen(
    ratingUiState: RatingUiState,
    ratingState: MutableIntState,
    onSubmitRating: (Int) -> Unit,
    onDeleteRating: () -> Unit,
    onCancelPress: () -> Unit,
) {

    val buttonModifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 32.dp)


    Scaffold(topBar = {
        TopBarX(title = ratingUiState.movieName ?: "" , onBackPress = onCancelPress)
    }) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Box(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .weight(1F)
                ) {
                    AsyncImageX(imageSrc = "${Constant.TMDB_IMAGE_URI_HIGH}/${ratingUiState.posterPath}")
                }

                Text(
                    modifier = Modifier.padding(
                        vertical = 16.dp
                    ), text = buildAnnotatedString {
                        append(text = "Your Rating : ")
                        withStyle(
                            style = StylesX.labelLarge.copy(color = Darkness.light).toSpanStyle()
                        ) {
                            append(text = ratingState.intValue.toString())
                        }
                    }, style = StylesX.bodyMedium
                )

                RatingStarRow(rating = ratingState)

                WXButton(text = "Rate",
                    enabled = ratingUiState.currentRating != ratingState.intValue,
                    modifier = Modifier.padding(
                        start = 20.dp, end = 20.dp, bottom = 8.dp, top = 16.dp
                    ),
                    onClick = {
                        onSubmitRating(ratingState.intValue)
                    })

                if (ratingUiState.isRated == false) {
                    WXButton(text = "Delete",
                        modifier = buttonModifier,
                        containerColor = Darkness.danger,
                        contentColor = Darkness.light,
                        onClick = {
                            onDeleteRating()
                        })
                } else {
                    WXButton(text = "Cancel",
                        modifier = buttonModifier,
                        containerColor = Darkness.midnight,
                        contentColor = Darkness.light,
                        onClick = {
                            onCancelPress()
                        })
                }


            }

            if (ratingUiState.isLoading == true) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Darkness.stillness.copy(alpha = 0.5F)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Darkness.rise,
                    )
                }
            }

        }
    }
}


@Composable
fun RatingStarRow(rating: MutableIntState) {


    Row {
        (1..10).toList().map { index ->
            Icon(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickable {
                        rating.intValue = index
                    },
                imageVector = Icons.Filled.StarRate,
                contentDescription = "Star Rating Icon",
                tint = if (rating.intValue >= index) Darkness.rise else Darkness.grey
            )

        }
    }
}