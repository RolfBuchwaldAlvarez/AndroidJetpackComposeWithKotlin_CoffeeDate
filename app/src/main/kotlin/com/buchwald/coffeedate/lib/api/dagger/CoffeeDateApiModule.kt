package com.buchwald.coffeedate.lib.api.dagger

import com.buchwald.coffeedate.feature.coffeeplanner.data.api.CoffeeDateApi
import com.buchwald.coffeedate.lib.api.ApiErrorsParser
import com.buchwald.coffeedate.lib.api.RetrofitApiErrorsParser
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [RetrofitModule::class])
internal class CoffeeDateApiModule {

    @Provides
    fun provideCoffeeDateApi(retrofit: Retrofit): CoffeeDateApi = retrofit.create(CoffeeDateApi::class.java)

    @Provides
    fun provideApiErrorParser(retrofit: Retrofit): ApiErrorsParser = RetrofitApiErrorsParser(retrofit)
}