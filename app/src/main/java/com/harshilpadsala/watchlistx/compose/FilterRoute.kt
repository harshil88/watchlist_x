package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.TopBarX
import com.harshilpadsala.watchlistx.compose.components.base_x.GenreChipX
import com.harshilpadsala.watchlistx.compose.components.base_x.TextFieldComponent
import com.harshilpadsala.watchlistx.constants.DateX
import com.harshilpadsala.watchlistx.data.res.list.GenreContent
import com.harshilpadsala.watchlistx.data.res.list.KeywordContent
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.FilterUiState
import com.harshilpadsala.watchlistx.vm.FilterViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


//Todo : Bug-Fix-Default-Dates-Not-Being-Shown

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
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val shouldShowDatePicker = remember {
        mutableStateOf(false)
    }

    val fromDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = 1704738600, initialDisplayedMonthMillis = 1704738600
    )

    val toDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = DateX.getCurrentDateTimeStamp()
    )

    LaunchedEffect(searchController.value) {
        //viewModel.searchKeywords(searchController.value)
        viewModel.searchKeywordsWithDebouncing(searchController.value)
    }

    LaunchedEffect(keywordSheetState.isVisible){
        if(!keywordSheetState.isVisible){
            focusManager.clearFocus()
            searchController.value = ""
        }
    }



    FilterScreen(filterUiState = filterUiState.value,
        keywordSheetState = keywordSheetState,
        dateInitialController = dateInitialController,
        searchController = searchController,
        onBackClick = onBackClick,
        fromDatePickerState = fromDatePickerState,
        toDatePickerState = toDatePickerState,
        onSearchKeywordClick = {
            scope.launch {
                if (!keywordSheetState.isVisible) {
                    keywordSheetState.show()
                    searchFocusRequester.requestFocus()
                }
            }

        },
        selectedKeywordsCallback = {
            Log.i("SelectedGendersCallback", it.toString())
        },
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
    filterUiState: FilterUiState,
    keywordSheetState: ModalBottomSheetState,
    focusRequester: FocusRequester,
    searchController: MutableState<String>,
    dateInitialController: MutableState<String>,
    fromDatePickerState: DatePickerState,
    toDatePickerState: DatePickerState,
    onSearchKeywordClick: () -> Unit,
    onSelectKeywordClick: (KeywordContent) -> Unit,
    selectedKeywordsCallback: (List<KeywordContent>) -> Unit,
    onBackClick: () -> Unit,
) {


    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxHeight(),
        sheetShape = RoundedCornerShape(
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

        Scaffold(topBar = { TopBarX(title = "Filter", onBackPress = {}) }) { paddingValues ->
            Column {
                DateSelectorRow(
                    paddingValues = paddingValues,
                    fromDatePickerState = fromDatePickerState,
                    toDatePickerState = toDatePickerState,
                )
                if (filterUiState.genres != null) {
                    GenderFilterChips(
                        modifier = Modifier.padding(
                            vertical = 16.dp,
                        ),
                        genres = filterUiState.genres!!,
                        selectedGendersCallback = {},
                    )
                }


                KeywordFilterChips(
                    keywords = filterUiState.selectedKeywords,
                    selectedKeywordsCallback = selectedKeywordsCallback,
                    onSearchKeywordClick = onSearchKeywordClick,
                )

            }
        }
    })


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorRow(
    paddingValues: PaddingValues,
    fromDatePickerState: DatePickerState,
    toDatePickerState: DatePickerState,
) {


    val fromDate = remember {
        mutableStateOf("1704738600")
    }

    val toDate = remember {
        mutableStateOf("1704738600")
    }


    val dateRangeType: MutableState<DateRangeType?> = remember {
        mutableStateOf(null)
    }

    val dateFormat = SimpleDateFormat("MM/dd/yyyy", java.util.Locale.ENGLISH)

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextFieldComponent(
            modifier = Modifier.weight(1F),
            textController = fromDate,
            onClick = {
                dateRangeType.value = DateRangeType.From
            },
            label = {
                Text(
                    text = "From", style = StylesX.labelMedium.copy(color = Darkness.grey)
                )
            },
        )

        TextFieldComponent(
            modifier = Modifier.weight(1F),
            textController = toDate,
            onClick = {
                dateRangeType.value = DateRangeType.To
            },
            label = {
                Text(
                    text = "To", style = StylesX.labelMedium.copy(color = Darkness.grey)
                )
            },
        )
    }

    if (dateRangeType.value != null) {
        DatePickerDialog(onDismissRequest = {
            dateRangeType.value = null
        }, confirmButton = {
            ElevatedButton(onClick = {
                if (dateRangeType.value == DateRangeType.From) {
                    fromDate.value =
                        dateFormat.format(Date(fromDatePickerState.selectedDateMillis!!))

                } else if (dateRangeType.value == DateRangeType.To) {
                    toDate.value = dateFormat.format(Date(toDatePickerState.selectedDateMillis!!))
                }
                dateRangeType.value = null
            }) {
                Text(text = "Done", style = StylesX.labelMedium.copy(color = Darkness.rise))
            }
        }) {
            DatePicker(
                state = if (dateRangeType.value == DateRangeType.From) fromDatePickerState else toDatePickerState,
                showModeToggle = false
            )
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenderFilterChips(
    genres: List<GenreContent>,
    selectedGendersCallback: (List<Int>) -> Unit,
    modifier: Modifier = Modifier,
) {

    val selectedGenders = mutableListOf<Int>()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier
    ) {
        genres.mapIndexed { index, genre ->
            item {
                GenreChipX(modifier = Modifier.padding(
                    start = if (index == 0) 20.dp else 0.dp,
                ),
                    selected = selectedGenders.contains(genre.id),
                    text = genre.name ?: "Unknown Genre",
                    onGenreClick = {
                        if (selectedGenders.contains(genre.id)) {
                            selectedGenders.remove(genre.id)
                        } else {
                            genre.id?.let { selectedGenders.add(genre.id) }
                        }
                        selectedGendersCallback(selectedGenders)
                    })
            }
        }
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
        TextFieldComponent(
            focusRequester = focusRequester,
            textController = searchController, modifier = Modifier.padding(
            16.dp
        ), leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Keyword",
                tint = Darkness.light,
            )
        }, placeholder = {
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


@Composable
fun KeywordFilterChips(
    keywords: List<KeywordContent>,
    onSearchKeywordClick: () -> Unit,
    selectedKeywordsCallback: (List<KeywordContent>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedKeywords = keywords.toMutableList()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier
    ) {
        keywords.mapIndexed { index, keyword ->
            item {
                GenreChipX(modifier = Modifier.padding(
                    start = if (index == 0) 20.dp else 0.dp,
                ),
                    selected = selectedKeywords.contains(keyword),
                    text = keyword.name ?: "Unknown Keyword",
                    onGenreClick = {
                        if (keyword.id != -1) {
                            if (selectedKeywords.contains(keyword)) {
                                selectedKeywords.remove(keyword)
                            } else {
                                keyword.id?.let { selectedKeywords.add(keyword) }
                            }
                            selectedKeywordsCallback(selectedKeywords)
                        } else {
                            onSearchKeywordClick()
                        }
                    })
            }
        }
        item {
            GenreChipX(
                modifier = Modifier.padding(
                    start = if (keywords.isEmpty()) 20.dp else 0.dp,
                ),
                selectable = false,
                selected = false,
                text = "Add Keywords",
                onGenreClick = onSearchKeywordClick,
            )
        }
    }
}





