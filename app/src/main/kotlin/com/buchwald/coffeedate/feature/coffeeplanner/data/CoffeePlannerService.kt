package com.buchwald.coffeedate.feature.coffeeplanner.data

import com.buchwald.coffeedate.feature.coffeeplanner.data.api.model.ApiEmployee
import com.buchwald.coffeedate.feature.coffeeplanner.data.api.model.ApiUpdateCoffeePairListRequest
import com.buchwald.coffeedate.lib.result.service.Result

interface CoffeePlannerService {
    suspend fun getEmployees(): Result<List<ApiEmployee>>
    suspend fun updateCoffeePairList(request: ApiUpdateCoffeePairListRequest): Result<Unit>
}