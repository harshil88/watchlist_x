package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX

@Composable
fun WXButton(
    text : String,
    modifier: Modifier = Modifier,
    enabled : Boolean = true,
    isLoading : Boolean = false,
    containerColor : Color = Darkness.rise,
    contentColor : Color = Darkness.midnight,
    onClick : ()-> Unit = {},
){
    ElevatedButton(
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5F)
        ),
        shape = RoundedCornerShape(8.dp),
        onClick =  onClick
    ) {
        if(isLoading){
            CircularProgressIndicator()
        }
        else{
            Text(text = text , style = StylesX.titleMedium.copy(color = contentColor))
        }
    }
}