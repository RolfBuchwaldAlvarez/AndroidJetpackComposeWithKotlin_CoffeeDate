package com.buchwald.coffeedate.lib.api.dagger

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
internal class OkHttpModule {

    @Provides
    fun provide(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }
}

private const val TIMEOUT_SECONDS = 20L