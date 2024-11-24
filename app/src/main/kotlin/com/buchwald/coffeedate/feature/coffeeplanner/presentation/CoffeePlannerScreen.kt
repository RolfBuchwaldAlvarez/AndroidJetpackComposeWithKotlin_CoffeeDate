package com.buchwald.coffeedate.feature.coffeeplanner.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.buchwald.coffeedate.R
import com.buchwald.coffeedate.app.ui.theme.OnSurface
import com.buchwald.coffeedate.app.ui.theme.OnSurfaceSecondary
import com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable.CircularAddButton
import com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable.CoffeeDayPairElement
import com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable.CoffeePlannerScaffold
import com.buchwald.coffeedate.feature.coffeeplanner.presentation.composable.SearchBar

@Composable
internal fun CoffeePlannerScreen(
    coffeePlannerViewModel: CoffeePlannerViewModel = viewModel(factory = CoffeePlannerViewModelFactory),
) {
    val state by coffeePlannerViewModel.state.collectAsStateWithLifecycle()

    CoffeePlannerScaffold(
        floatingActionButton = {
            CircularAddButton(
                modifier = Modifier
                    .size(40.dp)
                    .offset(y = (-680).dp)
            )
        },
        containerColor = Color.Transparent,
        backgroundDesign = {
            BackgroundDesign(
                modifier = it,
                backgroundImage = { BackgroundImage() },
                backgroundLogoText = { BackGroundTitle() })
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 345.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 12.dp,
                    end = 12.dp,
                )
        ) {
            SearchBar(onSearch = { searchInput ->
                when (state) {
                    is CoffeePlannerState.Init -> {}
                    is CoffeePlannerState.Ready -> {
                        coffeePlannerViewModel.filterPairList(searchInput)
                    }
                }
            })
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn {
                items(
                    when (state) {
                        is CoffeePlannerState.Init -> emptyList()
                        is CoffeePlannerState.Ready -> {
                            val readyState = state as CoffeePlannerState.Ready
                            readyState.filteredPairList ?: readyState.pairList

                        }
                    }
                ) { pair ->
                    val (weekday, names) = pair
                    CoffeeDayPairElement(
                        weekday,
                        names.first,
                        names.second,
                        R.drawable.img_person_male,
                        R.drawable.img_person_female,
                    )
                }
            }
        }
    }
}

@Composable
fun BackgroundDesign(
    modifier: Modifier = Modifier,
    backgroundImage: @Composable BoxScope.() -> Unit = {},
    backgroundLogoText: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier,
    ) {
        backgroundImage()
        backgroundLogoText()
    }
}

@Composable
fun BoxScope.BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.cup_of_coffee),
        contentDescription = stringResource(R.string.coffee_planner_screen_background_image),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .height(300.dp)
            .offset(x = (-40).dp, y = 80.dp)
            .align(Alignment.TopStart)
    )
}

@Composable
fun BoxScope.BackGroundTitle() {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, end = 16.dp)
            .align(Alignment.TopEnd)
    ) {
        Text(
            text = stringResource(R.string.coffee_planner_screen_background_title_first),
            color = OnSurface,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.alpha(0.8f)
        )
        Text(
            text = stringResource(R.string.coffee_planner_screen_background_title_second),
            color = OnSurfaceSecondary,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .graphicsLayer { rotationZ = 180f }
        )
    }
}