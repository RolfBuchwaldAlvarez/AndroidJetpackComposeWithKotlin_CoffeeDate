package com.buchwald.coffeedate.lib.api.dagger

import com.buchwald.coffeedate.lib.api.CoffeeDateService

interface CoffeeDateServiceComponent {
    fun coffeeDateService(): CoffeeDateService

    companion object {
        lateinit var instance: CoffeeDateServiceComponent
    }
}