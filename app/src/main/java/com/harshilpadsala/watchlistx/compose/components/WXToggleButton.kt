package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX

@Composable
fun WXToggleIconButton(
    state : Boolean,
    icon : ImageVector,
    modifier: Modifier = Modifier,
    enabled : Boolean = true,
    onStateColor : Color,
    offStateColor : Color = Darkness.stillness,
    onClick : ()-> Unit = {},
){

        ElevatedButton(
            enabled = enabled,
            modifier = modifier
                .height(56.dp)
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (state) onStateColor else offStateColor,
                disabledContainerColor = if (state) onStateColor.copy(alpha = 0.5F) else offStateColor.copy(
                    alpha = 0.5F
                )
            ),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                2.dp,
                if (state) offStateColor else onStateColor
            ),
            onClick = onClick
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Toggle Button Content",
                tint = if (state) offStateColor else onStateColor
            )

    }
}


@Preview
@Composable
fun WXToggleIconButtonPreview(){
    WXToggleIconButton(
        state = true,
        icon = Icons.Filled.Favorite,
        onStateColor = Darkness.life,

    )
}