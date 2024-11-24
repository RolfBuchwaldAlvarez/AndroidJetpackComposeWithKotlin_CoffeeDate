package com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable.PairCard
import com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable.WeekdayBadge

@Composable
fun CoffeeDayPairElement(
    weekday: String,
    nameOne: String,
    nameTwo: String,
    imageRes1: Int,
    imageRes2: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 12.dp)
    ) {
        WeekdayBadge(weekday = weekday)

        Spacer(modifier = Modifier.width(12.dp))

        PairCard(nameOne = nameOne, nameTwo = nameTwo, imageRes1 = imageRes1, imageRes2 = imageRes2)
    }
}