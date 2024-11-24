package com.buchwald.coffeedate.feature.coffeeplanner.domain.model

import com.buchwald.coffeedate.feature.coffeeplanner.data.api.model.ApiEmployee

data class Employee(val name: String)

fun ApiEmployee.toEmployee(): Employee = Employee(name = this.name)