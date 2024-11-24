package com.buchwald.coffeedate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.buchwald.coffeedate.app.ui.theme.CoffeeDateTheme
import com.buchwald.coffeedate.feature.coffeeplanner.presentation.CoffeePlannerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeeDateTheme {
                CoffeePlannerScreen()
            }
        }
    }
}