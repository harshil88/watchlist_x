package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.harshilpadsala.watchlistx.compose.DateRangeType
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)

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
                dateRangeType.value = null
                if (dateRangeType.value == DateRangeType.From) {
                    fromDate.value =
                        dateFormat.format(Date(fromDatePickerState.selectedDateMillis!!))

                } else if (dateRangeType.value == DateRangeType.To) {
                    toDate.value = dateFormat.format(Date(toDatePickerState.selectedDateMillis!!))
                }
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
