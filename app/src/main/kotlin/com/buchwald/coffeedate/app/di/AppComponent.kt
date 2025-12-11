package com.buchwald.coffeedate.app.di

import com.buchwald.coffeedate.lib.api.dagger.CoffeeDateServiceComponent
import com.buchwald.coffeedate.lib.api.dagger.CoffeeDateServiceConfigurationModule
import com.buchwald.coffeedate.lib.api.dagger.CoffeeDateServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    CoffeeDateServiceModule::class,
    CoffeeDateServiceConfigurationModule::class,
])
interface AppComponent : CoffeeDateServiceComponent {

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}