package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.compose.components.TopBarX
import com.harshilpadsala.watchlistx.compose.components.base_x.TextFieldComponent
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import com.harshilpadsala.watchlistx.vm.FilterViewModel
import java.text.SimpleDateFormat
import java.util.Date

enum class DateRangeType {
    From, To
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterRoute(
    onApplyClick: () -> Unit, onBackClick: () -> Unit, viewModel: FilterViewModel = hiltViewModel()
) {
    val dateInitialController = remember {
        mutableStateOf("From")
    }

    val shouldShowDatePicker = remember {
        mutableStateOf(false)
    }

    val fromDatePickerState = rememberDatePickerState()
    val toDatePickerState = rememberDatePickerState()


    FilterScreen(
        dateInitialController,
        onBackClick = onBackClick,
        fromDatePickerState = fromDatePickerState,
        toDatePickerState = toDatePickerState
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    dateInitialController: MutableState<String>,
    fromDatePickerState: DatePickerState,
    toDatePickerState: DatePickerState,
    onBackClick: () -> Unit,
) {


    Scaffold(topBar = { TopBarX(title = "Filter", onBackPress = {}) }) { paddingValues ->

        DateSelectorRow(
            paddingValues = paddingValues,
            fromDatePickerState = fromDatePickerState,
            toDatePickerState = toDatePickerState,
        )
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
        mutableStateOf(fromDatePickerState.selectedDateMillis.toString())
    }

    val toDate = remember {
        mutableStateOf(toDatePickerState.selectedDateMillis.toString())
    }


    val dateRangeType: MutableState<DateRangeType?> = remember {
        mutableStateOf(null)
    }

    val dateFormat = SimpleDateFormat("MM/dd/yyyy", java.util.Locale.ENGLISH)




    Row {
        TextFieldComponent(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 16.dp)
                .weight(1F),
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
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(), start = 16.dp, end = 16.dp
                )
                .weight(1F),
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





