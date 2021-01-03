package ru.punkoff.testforeveryone.ui.your_tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.model.Test
import ru.punkoff.testforeveryone.ui.all_tests.TestsViewState

class YourTestsViewModel : ViewModel() {
    private val mainLiveData = MutableLiveData<TestsViewState>(TestsViewState.EMPTY)
    private val databaseHelper = LocalDatabase()

    init {
        viewModelScope.launch {
            val testsFromDatabase = databaseHelper.observeTests()
            val tests = ArrayList<Test>()
            for (test in testsFromDatabase) {
                tests.add(Test(test.title, test.body))
            }
            mainLiveData.value = TestsViewState.Value(tests)
        }
    }

    fun observeViewState(): LiveData<TestsViewState> = mainLiveData
}