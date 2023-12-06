package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.ui.theme.Darkness
import com.harshilpadsala.watchlistx.ui.theme.StylesX

@Composable
fun RatingRow(rating : Double, users : Int){


   Row(
       verticalAlignment = Alignment.CenterVertically
   ) {
       GlobalRatingCard(
           rating, users , modifier = Modifier.weight(1F)
       )
       YourRating(rating , modifier = Modifier.weight(1F))
   }
}

@Composable
fun GlobalRatingCard(rating : Double , users: Int , modifier : Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon( Icons.Filled.Star,modifier = Modifier.fillMaxWidth(), contentDescription = "Rating Star", tint = Color.Yellow)
        RatingSpannableString(rating, users)
        Text(text = users.toString(), textAlign = TextAlign.Center ,    style = StylesX.bodyMedium.copy(color = Darkness.grey),
            maxLines = 1)
    }
}

@Composable
fun YourRating(rating : Double ,   modifier : Modifier, ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,

    ) {
        Icon( Icons.Outlined.Star,modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), contentDescription = "Rating Star", tint = Darkness.light)
        Text(text = "Your Rating", textAlign = TextAlign.Center ,
            style = StylesX.bodyMedium,
            maxLines = 1,
            )
    }
}

@Composable
fun RatingSpannableString(rating : Double , users: Int){
    Text(text = buildAnnotatedString {
        append(text = "%.2f".format(rating))
        withStyle(style = StylesX.labelMedium.copy(color = Darkness.light).toSpanStyle()){
            append(text = "/10")
        }
    },
        style =  StylesX.labelLarge.copy(  color = Darkness.light))
}