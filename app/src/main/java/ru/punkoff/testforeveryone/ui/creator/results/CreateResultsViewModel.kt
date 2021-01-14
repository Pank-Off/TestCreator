package ru.punkoff.testforeveryone.ui.creator.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.item_fragment_results.view.*
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.model.Result

class CreateResultsViewModel : ViewModel() {

    private val databaseHelper = LocalDatabase()

    private val resultsList = mutableListOf<Result>()
    private val resultsLiveData = MutableLiveData<List<Result>>()
    fun saveTest() {
        viewModelScope.launch {
            databaseHelper.saveTest(Repository.test)
        }
    }

    fun setResults(frag: ResultsFragment) {
        resultsList.add(
            Result(
                frag.view?.textEditTextFrom?.text.toString(),
                frag.view?.textEditTextTo?.text.toString(),
                frag.view?.textInputTitle?.text.toString(),
                frag.view?.textInputDescription?.text.toString()
            )
        )
        resultsLiveData.value = resultsList
        Repository.setResults(resultsList)
    }

    override fun onCleared() {
        super.onCleared()
        resultsLiveData.value = emptyList()
    }
}