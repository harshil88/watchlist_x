package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX

@Composable
fun FullScreenErrorX(

    modifier: Modifier = Modifier,
    text: String = "Something Went Wrong",
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    modifier = Modifier.size(72.dp),
                    imageVector = Icons.Filled.Error,
                    tint = Darkness.danger,
                    contentDescription = "Something went wrong",
                )

            }
            Spacer(modifier = Modifier.padding(bottom = 16.dp))

            Text(text = text, style = StylesX.titleMedium)
        }
    }
}


@Preview
@Composable

fun ErrorXPreview() {
    FullScreenErrorX()
}