package com.harshilpadsala.watchlistx.compose.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshilpadsala.watchlistx.compose.components.base_x.GenreChipX
import com.harshilpadsala.watchlistx.data.res.list.KeywordContent

@Composable
fun KeywordFilterChips(
    keywords: List<KeywordContent>,
    onSearchKeywordClick: () -> Unit,
    selectedKeywordsCallback: (List<KeywordContent>) -> Unit,
    modifier: Modifier = Modifier,
) {
    //val selectedKeywords = keywords.toMutableList()
    Log.i("KeywordContentDebug" , keywords.toString())

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier
    ) {
        keywords.mapIndexed { index, keyword ->
            item {
                GenreChipX(modifier = Modifier.padding(
                    start = if (index == 0) 20.dp else 0.dp,
                ),
                    selected = keyword.isSelected,
                    text = keyword.name ?: "Unknown Keyword",
                    onGenreClick = {
                        if (keyword.id != -1) {
                            keyword.isSelected = !keyword.isSelected
                            selectedKeywordsCallback(keywords)

//                            if (selectedKeywords.contains(keyword)) {
//                                selectedKeywords.remove(keyword)
//                            } else {
//                                keyword.id?.let { selectedKeywords.add(keyword) }
//                            }
//                            selectedKeywordsCallback(selectedKeywords)
                        } else {
                            onSearchKeywordClick()
                        }
                    })
            }
        }
        item {
            GenreChipX(
                modifier = Modifier.padding(
                    start = if (keywords.isEmpty()) 20.dp else 0.dp,
                ),
                selectable = false,
                selected = false,
                text = "Add Keywords",
                onGenreClick = onSearchKeywordClick,
            )
        }
    }
}
