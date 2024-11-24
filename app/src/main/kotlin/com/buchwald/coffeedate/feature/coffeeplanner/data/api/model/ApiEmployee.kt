package com.buchwald.coffeedate.feature.coffeeplanner.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiEmployee(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: ApiAddress,
    val phone: String,
    val website: String,
    val company: ApiCompany,
)