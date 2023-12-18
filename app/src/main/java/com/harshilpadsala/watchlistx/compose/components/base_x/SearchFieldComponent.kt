package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX

@Composable
fun SearchFieldComponent(
     modifier: Modifier = Modifier
){

    val searchValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = searchValue.value,
        placeholder = { Text(text = "Search" , style = StylesX.labelMedium.copy(color = Darkness.midnight))},
        onValueChange = {
            value ->
            searchValue.value = value
        },
        shape = RoundedCornerShape(4.dp),
        textStyle = StylesX.titleMedium.copy(color = Darkness.stillness),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Darkness.light,
            focusedBorderColor = Darkness.water,
            unfocusedBorderColor = Darkness.water,
            cursorColor = Darkness.stillness,
            textColor = Darkness.stillness
        )
    )
}