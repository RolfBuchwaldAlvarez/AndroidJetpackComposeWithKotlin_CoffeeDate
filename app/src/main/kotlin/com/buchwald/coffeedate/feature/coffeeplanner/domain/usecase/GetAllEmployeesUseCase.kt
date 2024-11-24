package com.buchwald.coffeedate.feature.coffeeplanner.domain.usecase

import com.buchwald.coffeedate.feature.coffeeplanner.domain.model.Employee
import com.buchwald.coffeedate.feature.coffeeplanner.domain.model.toEmployee
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

internal interface GetAllEmployeesUseCase {
    suspend operator fun invoke(): Result<List<Employee>, Error>
}

@Module
internal interface GetAllEmployeesUseCaseModule {
    @Binds
    fun bind(impl: DefaultGetAllEmployeesUseCase): GetAllEmployeesUseCase
}

internal class DefaultGetAllEmployeesUseCase @Inject constructor(
    private val coffeeDateService: CoffeeDateService,
) : GetAllEmployeesUseCase {

    override suspend fun invoke(): Result<List<Employee>, Error> =
        coffeeDateService.getEmployees().mapResult(
            onSuccess = {
                it.map { apiEmployee -> apiEmployee.toEmployee() }
                    .shuffled()
                    .toSuccess()
            },
            onApiError = {
                it.userErrorMessage?.let { message -> knownFailure(message) }
                    ?: unknownFailure()
            },
            onGenericError = { unknownFailure() },
            onConnectivityError = { connectivityFailure() },
        )
}