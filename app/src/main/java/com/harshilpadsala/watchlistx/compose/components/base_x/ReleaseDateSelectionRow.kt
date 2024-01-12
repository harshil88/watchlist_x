package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.compose.DateRangeType
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectorRow(
    fromDatePickerState: DatePickerState,
    toDatePickerState: DatePickerState,
    modifier: Modifier = Modifier,

    ) {


    val fromDate = remember {
        mutableStateOf("Select")
    }

    val toDate = remember {
        mutableStateOf("Select")
    }


    val dateRangeType: MutableState<DateRangeType?> = remember {
        mutableStateOf(null)
    }

    val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)

    Row(
        modifier = modifier,
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

        Spacer(modifier = Modifier.padding(start = 16.dp))

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
                    fromDatePickerState.selectedDateMillis?.let {
                        fromDate.value = dateFormat.format(Date(it))
                    }

                } else if (dateRangeType.value == DateRangeType.To) {
                    toDatePickerState.selectedDateMillis?.let {
                        toDate.value = dateFormat.format(Date(it))
                    }
                }

                dateRangeType.value = null

            }) {
                Text(text = "Done", style = StylesX.labelMedium.copy(color = Darkness.rise))
            }
        }) {
            DatePicker(
                state = if (dateRangeType.value == DateRangeType.From) fromDatePickerState else toDatePickerState,
                showModeToggle = false,
            )
        }
    }

}
