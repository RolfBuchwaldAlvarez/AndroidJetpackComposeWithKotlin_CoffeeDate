package com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeekdayBadge(weekday: String) {
    Box(
        modifier = Modifier
            .width(70.dp)
            .fillMaxHeight()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(6.dp),
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = weekday,
            style = MaterialTheme.typography.titleMedium
                .copy(color = MaterialTheme.colorScheme.onSurface) // Textstil
        )
    }
}