package ru.punkoff.testforeveryone.ui.fragments.your_tests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.local.DatabaseProvider
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class YourTestsViewModel(private val databaseHelper: DatabaseProvider) : ViewModel() {
    private val mainLiveData = MutableLiveData<TestsViewState>(TestsViewState.EMPTY)

    init {
        viewModelScope.launch {
            getDataFromDb()
        }
    }

    fun deleteTest(id: Long) {
        viewModelScope.launch {
            databaseHelper.deleteTest(id)
            Log.d(javaClass.simpleName, "Delete Test: $id")
            getDataFromDb()
        }
    }

    private suspend fun getDataFromDb() {
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
                    test.maxScore,
                    test.createData,
                    test.type,
                    test.color,
                    test.testId
                )
            )
        }
        tests.reverse()
        mainLiveData.value = TestsViewState.Value(tests)
    }

    fun observeViewState(): LiveData<TestsViewState> = mainLiveData
}