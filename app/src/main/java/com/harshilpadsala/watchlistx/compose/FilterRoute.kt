package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.GenderFilterChips
import com.harshilpadsala.watchlistx.compose.components.KeywordFilterChips
import com.harshilpadsala.watchlistx.compose.components.RangeSliderX
import com.harshilpadsala.watchlistx.compose.components.SingleSliderX
import com.harshilpadsala.watchlistx.compose.components.SliderCategory
import com.harshilpadsala.watchlistx.compose.components.TopBarX
import com.harshilpadsala.watchlistx.compose.components.base_x.DateSelectorRow
import com.harshilpadsala.watchlistx.compose.components.base_x.TextFieldComponent
import com.harshilpadsala.watchlistx.data.res.list.KeywordContent
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.FilterUiState
import com.harshilpadsala.watchlistx.vm.FilterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


//Todo : Bug-Fix-Default-Dates-Not-Being-Shown
//Todo : Bug-In-Add-Keywords-Flow-Proper-State-Not-Being-Maintained

enum class DateRangeType {
    From, To
}


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FilterRoute(
    onApplyClick: () -> Unit, onBackClick: () -> Unit, viewModel: FilterViewModel = hiltViewModel()
) {

    val filterUiState = rememberUpdatedState(newValue = viewModel.filterUiState.value)

    val scope = rememberCoroutineScope()

    val dateInitialController = remember {
        mutableStateOf("From")
    }

    val searchController = remember {
        mutableStateOf("")
    }

    val searchFocusRequester = FocusRequester()

    val focusManager = LocalFocusManager.current

    val keywordSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )


    val fromDatePickerState = rememberDatePickerState(
    )

    val toDatePickerState = rememberDatePickerState(
    )

    LaunchedEffect(searchController.value) {
        //viewModel.searchKeywords(searchController.value)
        viewModel.searchKeywordsWithDebouncing(searchController.value)
    }

    LaunchedEffect(keywordSheetState.isVisible) {
        if (!keywordSheetState.isVisible) {
            focusManager.clearFocus()
            searchController.value = ""
        }
    }

    FilterScreen(filterUiState = filterUiState.value,
        keywordSheetState = keywordSheetState,
        dateInitialController = dateInitialController,
        searchController = searchController,
        onBackClick = onBackClick,
        onFloatingButtonClick = {},
        fromDatePickerState = fromDatePickerState,
        toDatePickerState = toDatePickerState,
        scope = scope,
        onSearchKeywordClick = {
            scope.launch {
                if (!keywordSheetState.isVisible) {
                    keywordSheetState.show()
                    searchFocusRequester.requestFocus()
                }
            }
        },
        selectedKeywordsCallback = {},
        onRuntimeChange = {},
        onUserScoreChange = {},
        onUserVotesChange = {},
        focusRequester = searchFocusRequester,
        onSelectKeywordClick = {
            focusManager.clearFocus()
            if (keywordSheetState.isVisible) {
                scope.launch {
                    keywordSheetState.hide()
                }
                viewModel.addKeyword(it)
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FilterScreen(
    onBackClick: () -> Unit,
    onFloatingButtonClick : () -> Unit,
    filterUiState: FilterUiState,
    keywordSheetState: ModalBottomSheetState,
    focusRequester: FocusRequester,
    searchController: MutableState<String>,
    dateInitialController: MutableState<String>,
    fromDatePickerState: DatePickerState,
    toDatePickerState: DatePickerState,
    scope: CoroutineScope,
    onSearchKeywordClick: () -> Unit,
    onSelectKeywordClick: (KeywordContent) -> Unit,
    selectedKeywordsCallback: (List<KeywordContent>) -> Unit,
    onUserScoreChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onUserVotesChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onRuntimeChange: (Float) -> Unit,
) {

    ModalBottomSheetLayout(modifier = Modifier.fillMaxHeight(), sheetShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
    ), sheetState = keywordSheetState, sheetContent = {
        SearchKeywordSheetContent(
            searchController = searchController,
            searchResults = filterUiState.searchKeywords ?: listOf(),
            onSelectKeywordClick = onSelectKeywordClick,
            focusRequester = focusRequester,
        )
    }, content = {
        Scaffold(topBar = { TopBarX(title = "Filter", onBackPress = {}) }, floatingActionButton = {
            FloatingActionButton(onClick = onFloatingButtonClick , backgroundColor = Darkness.rise) {
                Icon(imageVector = Icons.Filled.Done, contentDescription = "Action Done Button" , tint = Darkness.stillness)
            }
        }) { paddingValues ->

            LazyColumn {

                item {
                    DateSelectorRow(
                        modifier = Modifier.padding(
                            top = paddingValues.calculateTopPadding(),
                            start = 16.dp,
                            end = 16.dp,
                        ),
                        fromDatePickerState = fromDatePickerState,
                        toDatePickerState = toDatePickerState,
                    )
                }

                item {
                    if (filterUiState.genres != null) {
                        GenderFilterChips(
                            modifier = Modifier.padding(
                                vertical = 16.dp,
                            ),
                            genres = filterUiState.genres!!,
                            selectedGendersCallback = {},
                        )
                    }
                }

                item {
                    KeywordFilterChips(
                        keywords = filterUiState.selectedKeywords,
                        selectedKeywordsCallback = selectedKeywordsCallback,
                        onSearchKeywordClick = onSearchKeywordClick,
                    )
                }

                item {
                    SliderCard(text = "User Score : ") {
                        RangeSliderX(
                            initialRange = 5F..8F,
                            minValue = 0F,
                            maxValue = 10F,
                            sliderCategory = SliderCategory.UserScore,
                            onValueChange = onUserScoreChange,
                            scope = scope
                        )
                    }
                }

                item {
                    SliderCard(text = "Minimum User Votes : ") {
                        SingleSliderX(
                            initialValue = 120F,
                            minValue = 0F,
                            maxValue = 400F,
                            sliderCategory = SliderCategory.UserVotes,
                            onValueChange = onRuntimeChange,
                            scope = scope
                        )
                    }

                }

                item {
                    SliderCard(text = "Runtime : ") {
                        RangeSliderX(
                            initialRange = 100F..200F,
                            minValue = 0F,
                            maxValue = 500F,
                            sliderCategory = SliderCategory.Runtime,
                            onValueChange = onUserVotesChange,
                            scope = scope
                        )
                    }
                }


            }
        }
    })
}

@Composable
fun SliderCard(
    text: String,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Darkness.night
        )
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
            text = text,
            style = StylesX.titleMedium.copy(color = Darkness.light),
        )

        content()


    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchKeywordSheetContent(
    focusRequester: FocusRequester,
    searchController: MutableState<String>,
    searchResults: List<KeywordContent>,
    onSelectKeywordClick: (KeywordContent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Darkness.night)
    ) {
        TextFieldComponent(focusRequester = focusRequester,
            textController = searchController,
            modifier = Modifier.padding(
                16.dp
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Keyword",
                    tint = Darkness.light,
                )
            },
            placeholder = {
                Text(
                    text = "Search Keyword", style = StylesX.labelMedium.copy(
                        color = Darkness.light
                    )
                )
            })

        LazyColumn {
            items(count = searchResults.size) { index ->
                ListItem(modifier = Modifier.clickable {
                    onSelectKeywordClick(searchResults[index])
                }, text = {
                    Text(text = searchResults[index].name ?: "", style = StylesX.labelMedium)
                })
            }
        }
    }
}





