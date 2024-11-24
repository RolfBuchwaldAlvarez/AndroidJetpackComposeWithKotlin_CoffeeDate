package com.buchwald.coffeedate.lib.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiBody<out T>(
    val data: T,
    val meta: Meta? = null,
) {
    @Serializable
    data class Meta(
        val page: Int,
        @SerialName(value = "last_page") val lastPage: Boolean,
    )
}