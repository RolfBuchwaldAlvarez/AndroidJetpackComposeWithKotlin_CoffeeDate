package com.buchwald.coffeedate.feature.coffeeplanner.domain.usecase

import com.buchwald.coffeedate.feature.coffeeplanner.data.api.model.ApiUpdateCoffeePairListRequest
import com.buchwald.coffeedate.lib.api.CoffeeDateService
import com.buchwald.coffeedate.lib.error.Error
import com.buchwald.coffeedate.lib.error.Error.Companion.connectivityFailure
import com.buchwald.coffeedate.lib.error.Error.Companion.knownFailure
import com.buchwald.coffeedate.lib.error.Error.Companion.unknownFailure
import com.buchwald.coffeedate.lib.result.domain.Result
import com.buchwald.coffeedate.lib.result.domain.toSuccess
import com.buchwald.coffeedate.lib.result.service.mapResult
import com.buchwald.coffeedate.lib.result.service.userErrorMessage
import dagger.Binds
import dagger.Module
import javax.inject.Inject

internal interface SaveCoffeePairListUseCase {
    suspend operator fun invoke(coffeePairList: List<Pair<String, Pair<String, String>>>):
            Result<Unit, Error>
}

@Module
internal interface SaveCoffeePairListUseCaseModule {
    @Binds
    fun bind(impl: DefaultSaveCoffeePairListUseCase): SaveCoffeePairListUseCase
}

internal class DefaultSaveCoffeePairListUseCase @Inject constructor(
    private val coffeeDateService: CoffeeDateService,
) : SaveCoffeePairListUseCase {

    override suspend fun invoke(coffeePairList: List<Pair<String, Pair<String, String>>>):
            Result<Unit, Error> =
        coffeeDateService.updateCoffeePairList(coffeePairList.toApiCoffeePairList()).mapResult(
            onSuccess = { it.toSuccess() },
            onApiError = {
                it.userErrorMessage?.let { message -> knownFailure(message) }
                    ?: unknownFailure()
            },
            onGenericError = { unknownFailure() },
            onConnectivityError = { connectivityFailure() },

            )
}

private fun List<Pair<String, Pair<String, String>>>.toApiCoffeePairList():
        ApiUpdateCoffeePairListRequest = ApiUpdateCoffeePairListRequest(coffeePairList = this)