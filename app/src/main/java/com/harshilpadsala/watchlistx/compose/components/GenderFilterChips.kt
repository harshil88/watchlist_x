package com.harshilpadsala.watchlistx.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.compose.components.base_x.GenreChipX
import com.harshilpadsala.watchlistx.data.res.list.GenreContent

@Composable
fun GenderFilterChips(
    genres: List<GenreContent>,
    selectedGendersCallback: (List<Int>) -> Unit,
    modifier: Modifier = Modifier,
) {

    val selectedGenders = mutableListOf<Int>()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier
    ) {
        genres.mapIndexed { index, genre ->
            item {
                GenreChipX(modifier = Modifier.padding(
                    start = if (index == 0) 20.dp else 0.dp,
                ),
                    selected = selectedGenders.contains(genre.id),
                    text = genre.name ?: "Unknown Genre",
                    onGenreClick = {
                        if (selectedGenders.contains(genre.id)) {
                            selectedGenders.remove(genre.id)
                        } else {
                            genre.id?.let { selectedGenders.add(genre.id) }
                        }
                        selectedGendersCallback(selectedGenders)
                    })
            }
        }
    }
}