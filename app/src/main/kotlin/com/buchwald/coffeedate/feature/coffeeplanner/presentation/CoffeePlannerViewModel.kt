package com.buchwald.coffeedate.feature.coffeeplanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buchwald.coffeedate.feature.coffeeplanner.domain.usecase.GetCoffeePairListUseCase
import com.buchwald.coffeedate.lib.result.domain.Result
import com.ioki.textref.TextRef
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

internal abstract class CoffeePlannerViewModel : ViewModel() {
    abstract val state: StateFlow<CoffeePlannerState>
    abstract val errors: Flow<TextRef>
    abstract fun filterPairList(input: String)
}

@Module
internal interface CoffeePlannerViewModelModule {
    @Binds
    fun bind(impl: DefaultCoffeePlannerViewModel): CoffeePlannerViewModel
}

@CoffeePlannerViewModelScope
internal class DefaultCoffeePlannerViewModel @Inject constructor(
    private val getCoffeePairListUseCase: GetCoffeePairListUseCase,
) : CoffeePlannerViewModel() {

    private val mutableState = MutableStateFlow<CoffeePlannerState>(CoffeePlannerState.Init)
    override val state: StateFlow<CoffeePlannerState> = mutableState

    private val signaler = MutableSharedFlow<Signal>()
    override val errors: Flow<TextRef> = signaler.filterIsInstance<Signal.Error>()
        .map { it.message }

    init {
        viewModelScope.launch {
            when (val pairList = getCoffeePairListUseCase()) {
                is Result.Success -> {
                    mutableState.value = CoffeePlannerState.Ready(
                        pairList = pairList.data,
                    )
                }

                is Result.Failure -> signaler.emit(
                    Signal.Error(TextRef.string("Somethings went wrong here. Please try again."))
                )
            }
        }
    }

    override fun filterPairList(input: String) {
        val state = mutableState.value
        if (state !is CoffeePlannerState.Ready) return

        state.pairList.filter { pair ->
            val (firstPerson, secondPerson) = pair.second
            firstPerson.contains(input) || secondPerson.contains(input)
        }.let { filteredPairList ->
            mutableState.value = state.copy(filteredPairList = filteredPairList)
        }

    }

}

internal sealed interface CoffeePlannerState {
    data object Init : CoffeePlannerState

    data class Ready(
        val pairList: List<Pair<String, Pair<String, String>>> = emptyList(),
        val filteredPairList: List<Pair<String, Pair<String, String>>>? = null,
    ) : CoffeePlannerState
}

internal sealed interface Signal {
    data class Error(val message: TextRef) : Signal
}
