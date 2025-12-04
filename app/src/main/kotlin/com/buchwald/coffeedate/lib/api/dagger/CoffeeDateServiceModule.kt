package com.buchwald.coffeedate.lib.api.dagger

import com.buchwald.coffeedate.lib.api.CoffeeDateService
import com.buchwald.coffeedate.lib.api.DefaultCoffeeDateService
import dagger.Binds
import dagger.Module

@Module(includes = [CoffeeDateApiModule::class])
internal interface CoffeeDateServiceModule {
    @Binds
    fun bind(impl: DefaultCoffeeDateService): CoffeeDateService
}