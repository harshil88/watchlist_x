package com.harshilpadsala.watchlistx.data.res.list

import com.harshilpadsala.watchlistx.data.ImageDetails

data class MovieImages(
    val backdrops : List<ImageDetails>?
)

fun MovieImages.toImageList() : List<String>? =
     backdrops?.map {
         it.filePath?:""
    }?.toList()
