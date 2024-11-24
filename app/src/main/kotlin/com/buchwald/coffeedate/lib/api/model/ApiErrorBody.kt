package com.buchwald.coffeedate.lib.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ApiErrorBody(
    @SerialName(value = "api_errors") val apiErrors: List<ApiError> = emptyList(),
) {
    @Serializable
    data class ApiError(val message: String, val code: String)
}
