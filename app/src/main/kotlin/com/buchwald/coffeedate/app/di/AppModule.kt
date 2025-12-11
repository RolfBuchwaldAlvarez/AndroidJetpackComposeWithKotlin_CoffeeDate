package com.buchwald.coffeedate.app.di

import android.app.Application
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application
}