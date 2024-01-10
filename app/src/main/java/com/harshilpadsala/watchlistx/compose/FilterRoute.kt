package com.harshilpadsala.watchlistx.compose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.TopBarX
import com.harshilpadsala.watchlistx.compose.components.base_x.GenreChipX
import com.harshilpadsala.watchlistx.compose.components.base_x.TextFieldComponent
import com.harshilpadsala.watchlistx.constants.DateX
import com.harshilpadsala.watchlistx.data.res.list.GenreContent
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.FilterUiState
import com.harshilpadsala.watchlistx.vm.FilterViewModel
import java.text.SimpleDateFormat
import java.util.Date


//Todo : Bug-Fix-Default-Dates-Not-Being-Shown

enum class DateRangeType {
    From, To
}


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterRoute(
    onApplyClick: () -> Unit, onBackClick: () -> Unit, viewModel: FilterViewModel = hiltViewModel()
) {

    val filterUiState = rememberUpdatedState(newValue = viewModel.filterUiState.value)

    val dateInitialController = remember {
        mutableStateOf("From")
    }

    val shouldShowDatePicker = remember {
        mutableStateOf(false)
    }

    val fromDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = 1704738600, initialDisplayedMonthMillis = 1704738600
    )

    val toDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = DateX.getCurrentDateTimeStamp()
    )


    FilterScreen(
        filterUiState = filterUiState.value,
        dateInitialController,
        onBackClick = onBackClick,
        fromDatePickerState = fromDatePickerState,
        toDatePickerState = toDatePickerState
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    filterUiState: FilterUiState,
    dateInitialController: MutableState<String>,
    fromDatePickerState: DatePickerState,
    toDatePickerState: DatePickerState,
    onBackClick: () -> Unit,
) {


    Scaffold(topBar = { TopBarX(title = "Filter", onBackPress = {}) }) { paddingValues ->

        Column {

            Text(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding() + 20.dp,
                    start = 20.dp,
                ), text = "Release Date", style = StylesX.labelMedium.copy(color = Darkness.light)
            )


            DateSelectorRow(
                paddingValues = paddingValues,
                fromDatePickerState = fromDatePickerState,
                toDatePickerState = toDatePickerState,
            )

            Text(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 20.dp,
                ), text = "Genres", style = StylesX.labelMedium.copy(color = Darkness.light)
            )

            if (filterUiState.genres != null) {
                GenderFilterChips(
                    genres = filterUiState.genres!!,
                    selectedGendersCallback = {
                                              Log.i("SelectedGendersCallback" , it.toString())
                    },
                )
            }


        }
    }
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
) {

    val selectedGenders = mutableListOf<Int>()

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        genres.map { genre ->
            GenreChipX(selected = selectedGenders.contains(genre.id),
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





