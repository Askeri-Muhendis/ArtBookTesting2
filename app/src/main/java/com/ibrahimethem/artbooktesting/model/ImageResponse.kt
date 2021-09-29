package com.ibrahimethem.artbooktesting.model

data class ImageResponse (
    //apimizde 3 katman var bunlar total -> int ceviriyor -total hits:int çeviriyor -hits : liste çeviriyor
    val hits : List<ImageResult>,
    val total : Int,
    val totalHits : Int
)