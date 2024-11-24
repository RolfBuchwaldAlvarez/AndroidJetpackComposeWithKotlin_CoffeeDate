package com.buchwald.coffeedate.feature.coffeeplanner.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiAddress(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: ApiGeo,
)