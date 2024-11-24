package com.buchwald.coffeedate.feature.coffeeplanner.data.api

import com.buchwald.coffeedate.feature.coffeeplanner.data.api.model.ApiEmployee
import com.buchwald.coffeedate.feature.coffeeplanner.data.api.model.ApiUpdateCoffeePairListRequest
import com.buchwald.coffeedate.lib.api.model.ApiBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

internal interface CoffeeDateApi {

    @GET("/users")
    suspend fun getEmployees(): Response<List<ApiEmployee>>

    /*@GET("/coffee_pair_list")
    suspend fun getCoffeePairList(): Response<List<Pair<String, Pair<String, String>>>>*/

    @PATCH("/update_coffee_pair_list")
    suspend fun updateCoffeePairList(
        @Body body: ApiBody<ApiUpdateCoffeePairListRequest>
    ): Response<Unit>

}