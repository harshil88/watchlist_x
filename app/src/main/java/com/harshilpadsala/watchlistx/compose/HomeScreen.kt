package com.harshilpadsala.watchlistx.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.harshilpadsala.watchlistx.vm.MovieVM

@Composable
fun HomeScreen(
     movieViewModel : MovieVM = hiltViewModel<MovieVM>()
){
   Column(
       modifier = Modifier.fillMaxHeight(),
       verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Text(text = "Home")
       Text(text = "Another things ")
       ElevatedButton(onClick = {
           movieViewModel.fetchMovies()
       }) {

       }
   }
}
