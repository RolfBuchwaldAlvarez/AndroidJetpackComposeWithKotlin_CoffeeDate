package com.buchwald.coffeedate.lib.api.dagger

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Qualifier

@Module
class CoffeeDateServiceConfigurationModule {
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = baseUrl.toString()
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl

const val urlString = "https://jsonplaceholder.typicode.com"
val baseUrl = urlString.toHttpUrlOrNull()