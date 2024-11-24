package com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.buchwald.coffeedate.R
import com.buchwald.coffeedate.app.ui.theme.OnSurfaceSecondary
import com.buchwald.coffeedate.app.ui.theme.OnSurfaceVariant
import com.buchwald.coffeedate.app.ui.theme.SurfaceVariant

@Composable
fun SearchBar(
    placeholderText: String = stringResource(R.string.search_bar_placeholder_text),
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf("") } // Eingabetext verwalten

    BasicTextField(
        value = query,
        onValueChange = {
            query = it
            onSearch(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp) // Festgelegte Höhe
            .background(
                color = SurfaceVariant,
                shape = RoundedCornerShape(28.dp) // Abgerundete Ecken
            )
            .padding(horizontal = 12.dp, vertical = 8.dp), // Interner Textabstand
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = OnSurfaceVariant // Textfarbe
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = OnSurfaceSecondary,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholderText,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = OnSurfaceSecondary
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}