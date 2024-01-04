package com.harshilpadsala.watchlistx.compose.components.base_x

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.harshilpadsala.watchlistx.ui.theme.Darkness


@Composable
fun FullScreenLoaderX(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Darkness.stillness.copy(alpha = 0.5F)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Darkness.rise,
        )
    }
}
