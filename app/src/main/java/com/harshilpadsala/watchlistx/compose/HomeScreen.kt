package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.vm.MovieVM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
     movieViewModel : MovieVM = hiltViewModel<MovieVM>()
){

    var showBottomSheet = remember { mutableStateOf(false) }

   Column(
       modifier = Modifier.fillMaxHeight(),
       verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Text(text = "Home")
       ElevatedButton(onClick = {
          // movieViewModel.fetchMovies()
       }) {
       }
   }
}
