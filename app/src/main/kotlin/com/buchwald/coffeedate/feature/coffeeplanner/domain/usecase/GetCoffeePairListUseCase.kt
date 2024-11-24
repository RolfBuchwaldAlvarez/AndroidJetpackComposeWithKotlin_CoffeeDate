package com.buchwald.coffeedate.feature.coffeeplanner.domain.usecase

import com.buchwald.coffeedate.feature.coffeeplanner.domain.model.Employee
import com.buchwald.coffeedate.lib.error.Error
import com.buchwald.coffeedate.lib.result.domain.Result
import dagger.Binds
import dagger.Module
import javax.inject.Inject

internal interface GetCoffeePairListUseCase {
    suspend operator fun invoke(): Result<List<Pair<String, Pair<String, String>>>, Error>
}

@Module
internal interface GetCoffeePairListUseCaseModule {
    @Binds
    fun bind(impl: DefaultGetCoffeePairListUseCase): GetCoffeePairListUseCase
}

internal class DefaultGetCoffeePairListUseCase @Inject constructor(
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    // private val getCoffeePairListFromBackendUseCase: GetCoffeePairListFromBackendUseCase,
    // private val saveCoffeePairListUseCase: SaveCoffeePairListUseCase,
) : GetCoffeePairListUseCase {

    override suspend fun invoke(): Result<List<Pair<String, Pair<String, String>>>, Error> =
        // Mit Backend-Speichern der CoffeePairList sollte sie an dieser Stelle abgerufen werden, falls vorhanden
        when (val result = getAllEmployeesUseCase()) {
            is Result.Success -> {
                val namePairs = createNamePairs(result.data)
                val coffeePairList = createPairsWithAddedWeekdays(namePairs)

                // Wenn man die Liste im Backend speichern will, kann man das hier entkommentieren
                /*coroutineScope {
                    val saveCoffeePairListResult = async {
                        saveCoffeePairListUseCase(coffeePairList)
                    }.await()

                    when (saveCoffeePairListResult) {
                        is Result.Success -> Result.Success(coffeePairList)

                        is Result.Failure -> Result.Failure(Error.Unknown)
                    }
                }*/

                Result.Success(coffeePairList)
            }

            is Result.Failure -> result
        }

    private fun createNamePairs(employees: List<Employee>): List<Pair<String, String>> {
        val changingList = employees.toMutableList()
        val namePairList = mutableListOf<Pair<String, String>>()

        // Solange mehr als 2 Personen in der Liste sind
        while (changingList.size > 2) {
            val firstElement = changingList.removeAt(0) // Entferne das erste Element

            // Erstelle Paare zwischen dem ersten Element und den restlichen
            for (secondElement in changingList) {
                namePairList.add(firstElement.name to secondElement.name)
            }
        }

        // Wenn nur noch 2 Elemente übrig sind, kombiniere sie als letztes Paar
        if (changingList.size == 2) {
            val first = changingList[0]
            val second = changingList[1]
            namePairList.add(first.name to second.name)
        }

        namePairList.shuffle()

        return namePairList
    }

    private fun createPairsWithAddedWeekdays(namePairs: List<Pair<String, String>>):
            List<Pair<String, Pair<String, String>>> {

        val weekdays = listOf("Mo", "Di", "Mi", "Do", "Fr")
        val coffeePairList = mutableListOf<Pair<String, Pair<String, String>>>()

        // Füge Wochentage zyklisch zu den Paaren hinzu
        namePairs.forEachIndexed { index, namePair ->
            val weekday = weekdays[index % weekdays.size] // Zyklische Auswahl der Wochentage
            coffeePairList.add(Pair(weekday, namePair))
        }

        return coffeePairList
    }
}