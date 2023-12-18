package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX

@Composable
fun PrimaryButton(
    actionStatus: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit,
    pendingActionContent: @Composable RowScope.() -> Unit,
    actionDoneContent: @Composable RowScope.() -> Unit,

) {

    val actionDone = remember {
        mutableStateOf(actionStatus)
    }


    ElevatedButton(
        onClick = {
            actionDone.value = !actionDone.value
            onClick(actionDone.value)
        },
        modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (actionDone.value) Darkness.water else Darkness.rise,
        ),

        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            2.dp, Darkness.rise
        )
    ) {
        if(actionDone.value) actionDoneContent() else pendingActionContent()
    }
}

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
                 onClick: () -> Unit,){
    ElevatedButton(
        onClick = onClick,
        modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =  Darkness.rise,
        ),

        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            2.dp, Darkness.rise
        )
    ) {
        Icon(Icons.Filled.Add , contentDescription = "Add to Watchlist Icon" , tint = Darkness.stillness)
        Text(
            text = "Add to Watchlist",
            modifier = Modifier.padding(
                start = 24.dp
            ),
            style = StylesX.titleMedium.copy(color = Darkness.stillness),
            maxLines = 1
        )
    }
}

@Composable
fun AddedButton(   modifier: Modifier = Modifier,
                 onClick: () -> Unit,){
    ElevatedButton(
        onClick = onClick,
        modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Darkness.rise,
        ),

        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            2.dp, Darkness.rise
        )
    ) {    Icon(Icons.Filled.Done , contentDescription = "Added to Watchlist Icon" , tint = Darkness.light)
        Text(
            text = "Added to Watchlist",
            modifier = Modifier.padding(
                start = 24.dp
            ),
            style = StylesX.titleMedium.copy(color = Darkness.light),
            maxLines = 1
        )
    }
}