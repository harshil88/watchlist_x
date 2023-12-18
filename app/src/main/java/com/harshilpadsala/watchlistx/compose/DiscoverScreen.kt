package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.compose.components.base_x.ListItemX
import com.harshilpadsala.watchlistx.compose.components.base_x.SearchFieldComponent
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.constants.TvList
import com.harshilpadsala.watchlistx.constants.isScrolledToEnd
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.state.DiscoverMovieUiState
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.DiscoverVM
import kotlinx.coroutines.launch
import utils.LoaderX


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DiscoverScreen(
    discoverVM: DiscoverVM = hiltViewModel()
) {
    BottomSheetPage()
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetPage(discoverVM: DiscoverVM = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val selectedMediaType = remember {
        mutableStateOf(MediaType.Movie)
    }

    val selectedTabIndex = remember {
        mutableIntStateOf(0)
    }

    val movieChipState = remember {
        mutableStateOf(MovieList.Popular)
    }

    val tvChipState = remember {
        mutableStateOf(TvList.Popular)
    }

    val lazyListState = rememberLazyListState()

    val endOfListReached by remember {
        derivedStateOf {
            lazyListState.isScrolledToEnd()
        }
    }

    LaunchedEffect(endOfListReached) {
        if (selectedMediaType.value == MediaType.Movie ) {
            if(discoverVM.currentPage != 1){
                discoverVM.discoverMovieList(movieChipState.value)
            }
       //   discoverVM.discoverMovieList(movieChipState.value)
        } else {
//
        }
    }



    ModalBottomSheetLayout(
        sheetBackgroundColor = Darkness.night, sheetShape = RoundedCornerShape(
            topStart = 16.dp, topEnd = 16.dp
        ), sheetContent = {
            ModalBottomSheetContent(mediaType = selectedMediaType.value) { index ->
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (selectedMediaType.value == MediaType.Movie) {
                        movieChipState.value = MovieList.values()[index]
                        discoverVM.discoverMovieList(movieChipState.value)

                    } else {
                        tvChipState.value = TvList.values()[index]
                        discoverVM.discoverTvList(tvChipState.value)
                    }
                }
            }
        }, sheetState = sheetState
    ) {
        DiscoverPage(onChipClick = {
            scope.launch {
                sheetState.show()
            }
        },
            title = if (selectedMediaType.value == MediaType.Movie) movieChipState.value.name else tvChipState.value.name,
            onItemClick = {},
            selectedTabIndex = selectedTabIndex,
            lazyListState = lazyListState,
            onTabItemClick = { mediaType ->
                selectedMediaType.value = mediaType
                if (mediaType == MediaType.Movie) {
                    discoverVM.discoverMovieList(movieChipState.value)
                } else {
                    discoverVM.discoverTvList(tvChipState.value)
                }
            })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheetContent(mediaType: MediaType, onItemTap: (Int) -> Unit) {

    val filters = if (mediaType == MediaType.Movie) MovieList.values() else TvList.values()


    LazyColumn {
        items(count = filters.size) { index ->
            ListItem(modifier = Modifier.clickable {
                onItemTap(index)
            }, text = {
                Text(text = filters[index].name)
            })
        }
        item {}
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DiscoverPage(
    discoverVM: DiscoverVM = hiltViewModel(),
    onChipClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onTabItemClick: (MediaType) -> Unit,
    title: String,
    lazyListState: LazyListState,
    selectedTabIndex: MutableIntState
) {


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(bottom = 132.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        SearchFieldComponent(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        )

        CategoryTabBar(selectedTabIndex, onTabItemClick)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp),
        ) {
            FilterChip(leadingIcon = {

                Icon(
                    Icons.Filled.Timeline, contentDescription = "Timeline Icon"
                )
            }, selected = true, onClick = onChipClick, label = {
                Text(text = title)
            })
        }

        PopularMovieUiState(
            uiState = discoverVM.popularMovieListSuccessState,
            lazyListState = lazyListState
        )


    }
}

@Composable
fun PopularMovieUiState(uiState: MutableState<DiscoverMovieUiState>, lazyListState: LazyListState)
{
    when (val state = uiState.value) {
        is DiscoverMovieUiState.Initial, DiscoverMovieUiState.Loading -> LoaderX()
        is DiscoverMovieUiState.PopularMovieFailureUiState -> {
            Text(text = "Something went wrong")
        }

        is DiscoverMovieUiState.SuccessUiState -> {PopularMovieUiState(hasReachedEnd = state.hasReachedEnd,
            movies = state.movies,
            lazyListState = lazyListState,
            onItemClick = { movieId -> })}
    }
}

@Composable
fun PopularMovieUiState(
    hasReachedEnd: Boolean,
    movies: List<ListItemXData>,
    lazyListState: LazyListState,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(
            start = 16.dp, top = 16.dp, end = 16.dp
        ), state = lazyListState
    ) {
        items(
            count = if (!hasReachedEnd) movies.size + 1 else movies.size
        ) { index ->

            if (!hasReachedEnd && index == movies.size) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                   ) {
                    CircularProgressIndicator()
                }
            }

            else{
                Box(modifier = Modifier.padding(bottom = 8.dp)) {
                    ListItemX(
                        title = movies[index].title,
                        voteAverage = movies[index].voteAverage,
                        thumbnailPath = Constant.TMDB_IMAGE_URI_HIGH + movies[index].posterPath,
                        originalLanguage = movies[index].originalLanguage,
                        releaseDate = movies[index].releaseDate,
                        modifier = Modifier.height(80.dp),
                        onClick = {
                            onItemClick(movies[index].id)
                        },
                    )
                }
            }


        }
    }
}


@Composable
fun CategoryTabBar(selectedTabIndex: MutableIntState, onTabItemClick: (MediaType) -> Unit) {


    TabRow(selectedTabIndex = selectedTabIndex.intValue, divider = {}, indicator = { tabPositions ->

        Box(
            modifier = Modifier
                .tabIndicatorOffset(tabPositions[selectedTabIndex.intValue])
                .height(1.dp)
                .width(2.dp)
                .background(color = Darkness.light),
        ) {}

    }) {
        MediaType.values().mapIndexed { index, category ->
            Tab(selected = selectedTabIndex.intValue == index, onClick = {
                if (selectedTabIndex.intValue != index) {
                    selectedTabIndex.intValue = index
                    onTabItemClick(category)
                }
            }) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
                    text = category.name,
                    style = StylesX.labelMedium.copy(color = if (selectedTabIndex.intValue == index) Darkness.light else Darkness.grey)
                )
            }
        }
    }
}

