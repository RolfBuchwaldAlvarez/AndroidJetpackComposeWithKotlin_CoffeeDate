package com.buchwald.coffeedate.feature.coffeeplanner.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUpdateCoffeePairListRequest(
    @Json(name = "coffee_pair_list") val coffeePairList: List<Pair<String, Pair<String, String>>>,
)