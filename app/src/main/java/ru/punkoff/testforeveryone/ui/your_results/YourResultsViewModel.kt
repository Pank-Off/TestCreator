package ru.punkoff.testforeveryone.ui.your_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.data.local.room.ResultEntity

class YourResultsViewModel : ViewModel() {
    private val mainLiveData = MutableLiveData<ResultsViewState>(ResultsViewState.EMPTY)
    private val databaseHelper = LocalDatabase()

    init {
        viewModelScope.launch {
            val resultsFromDatabase = databaseHelper.observeResults()
            val results = ArrayList<ResultEntity>()
            for (result in resultsFromDatabase) {
                results.add(
                    ResultEntity(
                        0,
                        result.testTitle,
                        result.title,
                        result.body,
                        result.maxScore,
                        result.score,
                        result.color
                    )
                )
            }
            results.reverse()
            mainLiveData.value = ResultsViewState.Value(results)
        }
    }

    fun observeViewState(): LiveData<ResultsViewState> = mainLiveData
}