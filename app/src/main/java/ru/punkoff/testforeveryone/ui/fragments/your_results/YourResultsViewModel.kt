package ru.punkoff.testforeveryone.ui.fragments.your_results

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.local.DatabaseProvider
import ru.punkoff.testforeveryone.data.local.room.ResultEntity

class YourResultsViewModel(private val databaseHelper: DatabaseProvider) : ViewModel() {
    private val mainLiveData = MutableLiveData<ResultsViewState>(ResultsViewState.EMPTY)

    init {
        viewModelScope.launch {
            getDataFromDb()
        }
    }

    fun deleteResult(id: Long) {
        viewModelScope.launch {
            databaseHelper.deleteResult(id)
            Log.d(javaClass.simpleName, "Delete Result: $id")
            getDataFromDb()
        }
    }

    private suspend fun getDataFromDb() {
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
                    result.lastPlayData,
                    result.color,
                    result.resultId
                )
            )
        }
        results.reverse()
        mainLiveData.value = ResultsViewState.Value(results)
    }

    fun observeViewState(): LiveData<ResultsViewState> = mainLiveData
}