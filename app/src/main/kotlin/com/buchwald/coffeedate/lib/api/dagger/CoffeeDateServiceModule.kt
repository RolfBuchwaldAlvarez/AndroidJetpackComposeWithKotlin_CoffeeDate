package com.buchwald.coffeedate.lib.api.dagger

import com.buchwald.coffeedate.feature.coffeeplanner.data.api.CoffeeDateApi
import com.buchwald.coffeedate.lib.api.ApiErrorsParser
import com.buchwald.coffeedate.lib.api.DefaultCoffeeDateService
import com.buchwald.coffeedate.lib.api.CoffeeDateService
import dagger.Module
import dagger.Provides

@Module(includes = [CoffeeDateApiModule::class])
class CoffeeDateServiceModule {

    @Provides
    internal fun provideUndecoratedCoffeeDateService(
        coffeeDateApi: CoffeeDateApi,
        apiErrorsParser: ApiErrorsParser,
    ): CoffeeDateService = DefaultCoffeeDateService(
        coffeeDateApi,
        apiErrorsParser,
    )
}