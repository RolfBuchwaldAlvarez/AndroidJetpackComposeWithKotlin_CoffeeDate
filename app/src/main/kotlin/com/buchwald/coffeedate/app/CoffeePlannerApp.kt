package com.buchwald.coffeedate.app

import android.app.Application
import com.buchwald.coffeedate.app.di.AppModule
import com.buchwald.coffeedate.app.di.DaggerAppComponent
import com.buchwald.coffeedate.lib.api.dagger.CoffeeDateServiceComponent

class CoffeePlannerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createAppComponent(this)
    }

    private fun createAppComponent(application: Application) {
        val appComponent = DaggerAppComponent.factory()
            .create(AppModule(application))

        CoffeeDateServiceComponent.instance = appComponent
    }
}