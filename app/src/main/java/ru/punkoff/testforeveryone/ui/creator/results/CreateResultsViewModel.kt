package ru.punkoff.testforeveryone.ui.creator.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.item_fragment_results.view.*
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.DataHelper
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.data.local.DatabaseProvider
import ru.punkoff.testforeveryone.data.remote.FirebaseProvider
import ru.punkoff.testforeveryone.model.Result

class CreateResultsViewModel(
    private val databaseHelper: DatabaseProvider,
    private val firebaseProvider: FirebaseProvider,
    test: TempTest
) : ViewModel() {

    private val resultsLiveData = MutableLiveData<List<Result>>()
    private val testLiveData = MutableLiveData<TempTest>()
    private val resultsList = mutableListOf<Result>()

    init {
        testLiveData.value = test
    }

    fun saveTest(createTestText: String) {
        val date = createTestText + DataHelper.parseDate()
        testLiveData.value?.setDataCreate(date)
        viewModelScope.launch {
            testLiveData.value?.getTest()?.let { databaseHelper.saveTest(it) }
        }
    }

    fun pushTest() {
        viewModelScope.launch {
            testLiveData.value?.getTest()?.let { firebaseProvider.pushTest(it) }
        }
    }

    fun clearResultsList() {
        resultsList.clear()
        resultsLiveData.value = resultsList
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
        testLiveData.value?.setResults(resultsList)
    }

    fun getTestLiveData() = testLiveData

    override fun onCleared() {
        super.onCleared()
        resultsLiveData.value = emptyList()
    }
}