package com.buchwald.coffeedate.feature.coffeeplanner.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiGeo(
    val lat: String,
    val lng: String,
)