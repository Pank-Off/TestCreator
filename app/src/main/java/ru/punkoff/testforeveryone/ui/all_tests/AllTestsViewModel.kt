package ru.punkoff.testforeveryone.ui.all_tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.data.Repository

class AllTestsViewModel : ViewModel() {
    private val mainLiveData = MutableLiveData<TestsViewState>(TestsViewState.EMPTY)

    init {
        mainLiveData.value = TestsViewState.Value(Repository.tests)
    }

    fun observeViewState(): LiveData<TestsViewState> = mainLiveData
}