package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.Constant
import com.harshilpadsala.watchlistx.compose.components.TopBarX
import com.harshilpadsala.watchlistx.compose.components.base_x.ListItemX
import com.harshilpadsala.watchlistx.constants.MediaType
import com.harshilpadsala.watchlistx.constants.MovieList
import com.harshilpadsala.watchlistx.constants.TvList
import com.harshilpadsala.watchlistx.constants.isScrolledToEnd
import com.harshilpadsala.watchlistx.data.res.model.ListItemXData
import com.harshilpadsala.watchlistx.state.DiscoverMovieUiState
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.vm.DiscoverUiState
import com.harshilpadsala.watchlistx.vm.DiscoverVM

//todo : Learn when and why to use rememberUpdatedState

//todo : Bug Padding Values in Top Bar

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiscoverRoute(
    onMediaClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiscoverVM = hiltViewModel()
) {

    val uiState = rememberUpdatedState(newValue = viewModel.discoverUiState.value)

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val lazyListState = rememberLazyListState()

    val endOfListReached by remember {
        derivedStateOf {
            lazyListState.isScrolledToEnd()
        }
    }



    if (
        endOfListReached
        && lazyListState.isScrollInProgress
        && uiState.value.isLoading == false
        && !uiState.value.hasReachedEnd) {
        viewModel.nextPage()
    }

    Log.i("FunnyUiState" , "Updating Ui State")
    Log.i("FunnyUiState" , uiState.value.movies.toString())
    Log.i("FunnyUiState" , uiState.value.currentPage.toString())
    Log.i("FunnyUiState" , uiState.value.movies.toString())

    DiscoverScreen(
        uiState = uiState.value,
        lazyListState = lazyListState,
        onBackClick = {},
        onFilterListClick = {},
    )


//    LaunchedEffect(endOfListReached) {
//        if (viewModel.selectedMediaType.value == MediaType.Movie) {
//            if (viewModel.currentPage != 1) {
//                viewModel.discoverMovieList(viewModel.movieChipState.value)
//            }
//        } else {
//
//        }
//    }


//    if (uiState.value is DiscoverMovieUiState.SuccessUiState) {
//        if ((uiState.value as DiscoverMovieUiState.SuccessUiState).currentPage == 1) {
//            scope.launch {
//                lazyListState.scrollToItem(0)
//            }
//        }
//    }


//    DiscoverScreen(uiState = uiState.value,
//        lazyListState = lazyListState,
//        modalBottomSheetState = sheetState,
//        movieChipState = viewModel.movieChipState.value,
//        tvChipState = viewModel.tvChipState.value,
//        selectedMediaType = viewModel.selectedMediaType.value,
//        onChipClick = {
//            scope.launch {
//                sheetState.show()
//            }
//        },
//        onItemClick = onMediaClick,
//        onTabItemClick = {
//            viewModel.onTabChange(it)
//            viewModel.resetCalled = true
//        },
//        onSearchClick = onSearchClick,
//        sheetItemClick = {
//            scope.launch {
//                sheetState.hide()
//                viewModel.toggleSheet(it)
//            }
//        })
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiscoverScreen(
    uiState: DiscoverUiState,
    onBackClick: () -> Unit,
    onFilterListClick: () -> Unit,
    lazyListState: LazyListState,
) {
    Scaffold(
        topBar = {
//            TopBarX(title = "Discover Movies", actions = {
//                IconButton(onClick = onFilterListClick) {
//                    Icon(
//                        imageVector = Icons.Filled.FilterList,
//                        contentDescription = "Go To Discover Movie Filters Screen",
//                        tint = Darkness.light,
//                    )
//                }
//            }, onBackPress = onBackClick
//            )
        }
    ) { paddingValues ->
        Log.i("PaddingDebug", paddingValues.calculateTopPadding().toString())
        Spacer(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()))

        Log.i("FunnyUiState CurrentPage", uiState.currentPage.toString())
        Log.i("FunnyUiState List Length", uiState.movies?.size.toString())


        if (uiState.movies != null) {
            MoviesList(
                hasReachedEnd = false,
                movies = uiState.movies!!,
                lazyListState = lazyListState,
                onItemClick = {})
        }
    }
}


//scope.launch {
//    sheetState.hide()
//}.invokeOnCompletion {
//    if (selectedMediaType.value == MediaType.Movie) {
//        if( movieChipState.value != MovieList.values()[index]){
//            movieChipState.value = MovieList.values()[index]
//            discoverVM.reset()
//            discoverVM.discoverMovieList(movieChipState.value)
//
//        }
//
//    } else {
//        tvChipState.value = TvList.values()[index]
//        discoverVM.discoverTvList(tvChipState.value)
//    }
//}

//scope.launch {
//    sheetState.show()
//}
//
//mediaType ->
//selectedMediaType.value = mediaType
//if (mediaType == MediaType.Movie) {
//    discoverVM.discoverMovieList(movieChipState.value)
//} else {
//    discoverVM.discoverTvList(tvChipState.value)
//}
//}


//
//val scope = rememberCoroutineScope()


@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun DiscoverSscreen(
    uiState: DiscoverMovieUiState,
    modalBottomSheetState: ModalBottomSheetState,
    selectedMediaType: MediaType,
    movieChipState: MovieList,
    tvChipState: TvList,
    lazyListState: LazyListState,
    onChipClick: () -> Unit,
    onTabItemClick: (MediaType) -> Unit,
    onSearchClick: () -> Unit,
    sheetItemClick: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
) {


    ModalBottomSheetLayout(
        sheetBackgroundColor = Darkness.night, sheetShape = RoundedCornerShape(
            topStart = 16.dp, topEnd = 16.dp
        ), sheetContent = {
            ModalBottomSheetContent(mediaType = selectedMediaType, onItemTap = sheetItemClick)
        }, sheetState = modalBottomSheetState
    ) {
        DiscoverPage(
            uiState = uiState,
            onChipClick = onChipClick,
            title = if (selectedMediaType == MediaType.Movie) movieChipState.name else tvChipState.name,
            onItemClick = onItemClick,
            lazyListState = lazyListState,
            onTabItemClick = onTabItemClick
        )
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
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverPage(
    uiState: DiscoverMovieUiState,
    lazyListState: LazyListState,
    onChipClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onTabItemClick: (MediaType) -> Unit,
    title: String,

    ) {


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(bottom = 132.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


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


    }
}

//@SuppressLint("CoroutineCreationDuringComposition")
//@Composable
//fun PopularMovieUiState(
//    uiState: DiscoverMovieUiState, lazyListState: LazyListState, onItemClick: (Int) -> Unit
//) {
//    when (uiState) {
//        is DiscoverMovieUiState.Initial, DiscoverMovieUiState.Loading -> LoaderX()
//        is DiscoverMovieUiState.PopularMovieFailureUiState -> {
//            Text(text = "Something went wrong")
//        }
//
//        is DiscoverMovieUiState.SuccessUiState -> {
//
//
//            PopularMovieUiState(
//                hasReachedEnd = uiState.hasReachedEnd,
//                movies = uiState.movies,
//                lazyListState = lazyListState,
//                onItemClick = onItemClick
//            )
//        }
//    }
//}

@Composable
fun MoviesList(
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Box(modifier = Modifier.padding(bottom = 8.dp)) {
                    ListItemX(
                        mediaId = movies[index].id,
                        title = movies[index].title,
                        voteAverage = movies[index].voteAverage,
                        thumbnailPath = Constant.TMDB_IMAGE_URI_HIGH + movies[index].posterPath,
                        originalLanguage = movies[index].originalLanguage,
                        releaseDate = movies[index].releaseDate,
                        modifier = Modifier.height(80.dp),
                        onItemClick = onItemClick,
                    )
                }
            }


        }
    }
}


//@Composable
//fun CategoryTabBar(onTabItemClick: (MediaType) -> Unit) {
//
//    val selectedTabIndex = remember {
//        mutableIntStateOf(0)
//    }
//
//    TabRow(selectedTabIndex = selectedTabIndex.intValue, divider = {}, indicator = { tabPositions ->
//
//        Box(
//            modifier = Modifier
//                .tabIndicatorOffset(tabPositions[selectedTabIndex.intValue])
//                .height(1.dp)
//                .width(2.dp)
//                .background(color = Darkness.light),
//        )
//
//    }) {
//        MediaType.values().mapIndexed { index, category ->
//            Tab(selected = selectedTabIndex.intValue == index, onClick = {
//                if (selectedTabIndex.intValue != index) {
//                    selectedTabIndex.intValue = index
//                    onTabItemClick(category)
//                }
//            }) {
//                Text(
//                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
//                    text = category.name,
//                    style = StylesX.labelMedium.copy(color = if (selectedTabIndex.intValue == index) Darkness.light else Darkness.grey)
//                )
//            }
//        }
//    }
//}

@Preview
@Composable
fun DiscoverScreenPreview() {
    TopBarX(title = "Discover Movies", actions = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.FilterList,
                contentDescription = "Go To Discover Movie Filters Screen",
                tint = Darkness.light,
            )
        }
    }, onBackPress = {}
    )
}

