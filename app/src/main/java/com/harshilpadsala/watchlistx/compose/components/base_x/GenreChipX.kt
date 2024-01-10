package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreChipX(
    text: String,
    modifier : Modifier = Modifier,
    selected: Boolean = false,
    selectable : Boolean = true,
    onGenreClick: () -> Unit,
    ) {

    val isSelected = remember {
        mutableStateOf(selected)
    }

    FilterChip(
        modifier = modifier,
        onClick = {
            if(selectable){
                isSelected.value = !isSelected.value
            }
            onGenreClick()
        },
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Darkness.water
        ),
        leadingIcon = {
            if (isSelected.value)
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check Icon",
                    tint = Darkness.night,
                )
        },
        label = {
            Text(text = text, style = StylesX.labelMedium.copy(color = if(isSelected.value) Darkness.night else Darkness.light))
        }, selected = isSelected.value, colors = FilterChipDefaults.filterChipColors(
            containerColor =Darkness.night,
            selectedContainerColor = Darkness.rise
        )
    )
}