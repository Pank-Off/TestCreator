package ru.punkoff.testforeveryone.ui.your_tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.ui.all_tests.TestsViewState

class YourTestsViewModel : ViewModel() {
    private val mainLiveData = MutableLiveData<TestsViewState>(TestsViewState.EMPTY)
    private val databaseHelper = LocalDatabase()

    init {
        viewModelScope.launch {
            val testsFromDatabase = databaseHelper.observeTests()
            val tests = ArrayList<TestEntity>()
            for (test in testsFromDatabase) {
                tests.add(
                    TestEntity(
                        0,
                        test.title,
                        test.body,
                        test.questions,
                        test.results,
                        test.color
                    )
                )
            }
            mainLiveData.value = TestsViewState.Value(tests)
        }
    }

    fun observeViewState(): LiveData<TestsViewState> = mainLiveData
}