package com.buchwald.coffeedate.feature.coffeeplanner.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCompany(
    val name: String,
    val catchPhrase: String,
    val bs: String,
)