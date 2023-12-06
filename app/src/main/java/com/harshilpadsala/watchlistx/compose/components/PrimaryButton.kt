package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
    content : @Composable RowScope.() -> Unit,
){
    ElevatedButton(
        onClick = onClick , modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Yellow
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        content()
    }
}