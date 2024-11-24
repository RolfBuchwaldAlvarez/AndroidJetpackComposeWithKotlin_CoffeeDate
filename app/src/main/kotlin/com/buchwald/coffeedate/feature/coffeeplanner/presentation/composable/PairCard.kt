package com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun PairCard(nameOne: String, nameTwo: String, imageRes1: Int, imageRes2: Int) {
    Card(
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(start = 8.dp),
                horizontalArrangement = Arrangement.spacedBy((-20).dp),
            ) {
                Image(
                    painter = painterResource(id = imageRes1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape) // Runde Form
                        .border(1.dp, MaterialTheme.colorScheme.background, CircleShape)
                )
                Image(
                    painter = painterResource(id = imageRes2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.background, CircleShape)
                )
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "$nameOne & $nameTwo",
                    style = MaterialTheme.typography.bodyLarge
                        .copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

        }
    }
}