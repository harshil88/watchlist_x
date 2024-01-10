package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX


//Todo : Disable splash effect when text field is being clicked

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    textController: MutableState<String> = mutableStateOf(""),
) {
    OutlinedTextField(
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
            },
        value = textController.value,
        enabled = onClick == null,
        placeholder = placeholder,
        onValueChange = { value ->
            textController.value = value
        },
        label = label,
        shape = RoundedCornerShape(4.dp),
        textStyle = StylesX.titleMedium.copy(color = Darkness.light),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Darkness.water,
            focusedBorderColor = Darkness.light,
            unfocusedBorderColor = Darkness.light,
            cursorColor = Darkness.light,
            textColor = Darkness.light
        )
    )
}

@Preview
@Composable
fun DateSearchComponent() {
    val searchController = remember {
        mutableStateOf("")
    }

    TextFieldComponent(
        label = { Text(text = "From", style = StylesX.labelMedium.copy(color = Darkness.light)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Date Range Icon",
                tint = Darkness.light
            )
        },
        textController = searchController
    )
}