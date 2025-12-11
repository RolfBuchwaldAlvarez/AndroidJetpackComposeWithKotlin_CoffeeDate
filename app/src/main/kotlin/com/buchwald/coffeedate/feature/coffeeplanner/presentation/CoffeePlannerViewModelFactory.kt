package com.buchwald.coffeedate.feature.coffeeplanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.buchwald.coffeedate.feature.coffeeplanner.domain.usecase.GetAllEmployeesUseCaseModule
import com.buchwald.coffeedate.feature.coffeeplanner.domain.usecase.GetCoffeePairListUseCaseModule
import com.buchwald.coffeedate.feature.coffeeplanner.domain.usecase.SaveCoffeePairListUseCaseModule
import com.buchwald.coffeedate.lib.api.dagger.CoffeeDateServiceComponent
import dagger.Component

object CoffeePlannerViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val component = DaggerCoffeePlannerViewModelComponent.builder()
            .coffeeDateServiceComponent(CoffeeDateServiceComponent.instance)
            .build()
        @Suppress("UNCHECKED_CAST")
        return component.viewModel() as T
    }
}

@Component(
    modules = [
        CoffeePlannerViewModelModule::class,
        GetAllEmployeesUseCaseModule::class,
        GetCoffeePairListUseCaseModule::class,
        SaveCoffeePairListUseCaseModule::class,
    ],
    dependencies = [
        CoffeeDateServiceComponent::class,
    ]
)
internal interface CoffeePlannerViewModelComponent {
    fun viewModel(): CoffeePlannerViewModel
}
