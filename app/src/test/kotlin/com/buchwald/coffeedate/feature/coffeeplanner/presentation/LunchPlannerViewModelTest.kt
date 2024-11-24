package com.buchwald.coffeedate.feature.coffeeplanner.presentation

import com.buchwald.coffeedate.TestCoroutineRule
import com.buchwald.coffeedate.feature.coffeeplanner.domain.usecase.GetCoffeePairListUseCase
import com.buchwald.coffeedate.lib.result.domain.Result
import com.buchwald.coffeedate.lib.error.Error
import com.ioki.textref.TextRef
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
internal class CoffeePlannerViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val coffeePairList = listOf(
        ("Mo" to ("Bob" to "Alice")),
        ("Di" to ("Eve" to "Charlie")),
        ("Mi" to ("Mallory" to "Trent")),
        ("Do" to ("David" to "Sarah")),
        ("Fr" to ("Olivia" to "William")),
        ("Mo" to ("Sophia" to "James")),
        ("Di" to ("Mia" to "Benjamin")),
        ("Mi" to ("Emma" to "Lucas")),
        ("Do" to ("Ava" to "Henry")),
        ("Fr" to ("Isabella" to "Alexander")),
        ("Mo" to ("Riley" to "Michael")),
        ("Di" to ("Aria" to "Daniel")),
        ("Mi" to ("Aaliyah" to "Matthew")),
        ("Do" to ("Chloe" to "Samuel")),
        ("Fr" to ("Scarlett" to "Jackson")),
        ("Mo" to ("Ellie" to "Aiden")),
        ("Di" to ("Aubrey" to "Joseph")),
        ("Mi" to ("Addison" to "Sebastian")),
        ("Do" to ("Stella" to "David")),
        ("Fr" to ("Natalie" to "Carter"))
    )

    @Test
    fun `state is in Init as long as class init is running`() = runTest {
        val getCoffeePairListUseCaseResult = Result.Success(coffeePairList)
        val testViewModel = createTestViewModel(getCoffeePairListUseCaseResult)

        val state = testViewModel.state.first()

        expectThat(state).isEqualTo(CoffeePlannerState.Init)
    }

    @Test
    fun `state is Ready when everything succeeded`() = runTest {
        val getCoffeePairListUseCaseResult = Result.Success(coffeePairList)
        val testViewModel = createTestViewModel(getCoffeePairListUseCaseResult)
        advanceUntilIdle()

        val state = testViewModel.state.first()

        val expectedState = CoffeePlannerState.Ready(
            pairList = coffeePairList,
            filteredPairList = null
        )
        expectThat(state).isEqualTo(expectedState)
    }

    @Test
    fun `state is Ready with filtered list when filterPairList is called`() = runTest {
        val getCoffeePairListUseCaseResult = Result.Success(coffeePairList)
        val testViewModel = createTestViewModel(getCoffeePairListUseCaseResult)
        advanceUntilIdle()

        testViewModel.filterPairList("am")
        advanceUntilIdle()

        val state = testViewModel.state.first()

        val expectedState = CoffeePlannerState.Ready(
            pairList = coffeePairList,
            filteredPairList = listOf(
                ("Fr" to ("Olivia" to "William")),
                ("Mo" to ("Sophia" to "James")),
                ("Di" to ("Mia" to "Benjamin")),
                ("Do" to ("Chloe" to "Samuel")),
            ),
        )
        expectThat(state).isEqualTo(expectedState)
    }

    @Test
    fun `state is Ready with filtered list equal to coffeePairList when filterPairList is called with empty input`() = runTest {
        val getCoffeePairListUseCaseResult = Result.Success(coffeePairList)
        val testViewModel = createTestViewModel(getCoffeePairListUseCaseResult)
        advanceUntilIdle()

        testViewModel.filterPairList("")
        advanceUntilIdle()

        val state = testViewModel.state.first()

        val expectedState = CoffeePlannerState.Ready(
            pairList = coffeePairList,
            filteredPairList = coffeePairList,
            )
        expectThat(state).isEqualTo(expectedState)
    }

    @Test
    fun `error is emitted when coffeePairListUseCase fails`() = runTest {
        val getCoffeePairListUseCaseResult = Result.Failure(Error.Unknown)
        val testViewModel = createTestViewModel(getCoffeePairListUseCaseResult)

        val error = testViewModel.errors.first()

        expectThat(error).isEqualTo(TextRef.string("Somethings went wrong here. Please try again."))
    }
}

private fun createTestViewModel(
    getCoffeePairListUseCaseResult: Result<List<Pair<String, Pair<String, String>>>, Error>,
): CoffeePlannerViewModel {

    val fakeGetCoffeePairListUseCase = object : GetCoffeePairListUseCase {
        override suspend fun invoke(): Result<List<Pair<String, Pair<String, String>>>, Error> = getCoffeePairListUseCaseResult
    }

    return DefaultCoffeePlannerViewModel(
        fakeGetCoffeePairListUseCase
    )
}