package com.buchwald.coffeedate.lib.api

import com.buchwald.coffeedate.feature.coffeeplanner.data.CoffeePlannerService
import com.buchwald.coffeedate.feature.coffeeplanner.data.api.CoffeeDateApi
import com.buchwald.coffeedate.feature.coffeeplanner.data.api.model.ApiEmployee
import com.buchwald.coffeedate.feature.coffeeplanner.data.api.model.ApiUpdateCoffeePairListRequest
import com.buchwald.coffeedate.lib.api.model.ApiBody
import com.buchwald.coffeedate.lib.result.service.Result
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.ClosedChannelException
import kotlin.coroutines.cancellation.CancellationException

interface CoffeeDateService : CoffeePlannerService

internal class DefaultCoffeeDateService(
    private val coffeeDateApi: CoffeeDateApi,
    private val apiErrorsParser: ApiErrorsParser,
) : CoffeeDateService {

    override suspend fun getEmployees(): Result<List<ApiEmployee>> =
        apiCall { getEmployees() }

    override suspend fun updateCoffeePairList(request: ApiUpdateCoffeePairListRequest):
            Result<Unit> = apiCall { updateCoffeePairList(ApiBody(request)) }

    private suspend inline fun <R, reified T : Any> apiCall(
        crossinline block: suspend CoffeeDateApi.() -> Response<R>,
    ): Result<T> = try {
        val response = coffeeDateApi.block()
        if (response.isSuccessful) {
            mapSuccess(response)
        } else {
            mapApiError(response, apiErrorsParser)
        }
    } catch (e: Exception) {
        when {
            e is CancellationException -> throw e
            e.isConnectivityError -> Result.Error.Connectivity(e)
            else -> Result.Error.Generic(e)
        }
    }

}

internal inline fun <R, reified T : Any> mapSuccess(successfulResponse: Response<R>): Result.Success<T> {
    val body = successfulResponse.body()
    val meta = (body as? ApiBody<*>)?.meta
    val data = when (body) {
        null -> Unit as? T // For Void (no content) body
        is T -> body
        is ApiBody<*> -> when {
            body.data is T -> body.data
            T::class == String::class -> body.data.toString() as T
            else -> null
        }

        is ResponseBody -> when (T::class.java) {
            String::class.java -> body.string() as T
            ByteArray::class.java -> body.bytes() as T
            else -> null
        }

        else -> null
    } ?: throw IllegalArgumentException("Failed to convert body '$body' to type ${T::class.java}")
    return Result.Success(data, meta)
}

internal fun <R> mapApiError(
    failedResponse: Response<R>,
    errorsParser: ApiErrorsParser,
): Result.Error.Api {
    val code = failedResponse.code()
    val apiErrors = errorsParser.parse(failedResponse.errorBody())

    return Result.Error.Api.Generic(apiErrors, code)
}

internal val Throwable.isConnectivityError: Boolean
    get() =
        this is SocketException ||
                this is SocketTimeoutException ||
                this is UnknownHostException ||
                this is ClosedChannelException
