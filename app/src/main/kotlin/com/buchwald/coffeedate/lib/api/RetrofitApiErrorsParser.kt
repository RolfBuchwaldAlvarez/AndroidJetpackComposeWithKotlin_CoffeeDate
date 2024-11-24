package com.buchwald.coffeedate.lib.api

import com.buchwald.coffeedate.lib.api.model.ApiErrorBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.EOFException
import javax.inject.Inject

internal interface ApiErrorsParser {
    fun parse(errorBody: ResponseBody?): List<ApiErrorBody.ApiError>
}

private const val APPLICATION_JSON = "application/json; charset=utf-8"

internal class RetrofitApiErrorsParser @Inject constructor(retrofit: Retrofit) : ApiErrorsParser {

    private val converter: Converter<ResponseBody, ApiErrorBody> = retrofit
        .responseBodyConverter(ApiErrorBody::class.java, emptyArray())

    override fun parse(errorBody: ResponseBody?): List<ApiErrorBody.ApiError> = when {
        errorBody == null -> emptyList()
        errorBody.contentLength() == 0L -> emptyList()
        errorBody.contentType() != APPLICATION_JSON.toMediaType() -> emptyList()
        else -> errorBody.convert()
    }

    private fun ResponseBody.convert(): List<ApiErrorBody.ApiError> = try {
        converter.convert(this)?.apiErrors ?: emptyList()
    } catch (e: EOFException) {
        emptyList()
    } catch (e: Exception) {
        emptyList()
    }
}
