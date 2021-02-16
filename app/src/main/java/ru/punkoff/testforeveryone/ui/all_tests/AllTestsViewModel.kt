package ru.punkoff.testforeveryone.ui.all_tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.punkoff.testforeveryone.data.remote.FirebaseProvider
import ru.punkoff.testforeveryone.ui.your_tests.TestsViewState

class AllTestsViewModel(private val firebaseProvider: FirebaseProvider) : ViewModel() {
    private val allTestsLiveData = MutableLiveData<TestsViewState>(TestsViewState.EMPTY)

    init {
        getRequest()
    }

    fun observeViewState(): LiveData<TestsViewState> = allTestsLiveData

    fun getRequest() {
        allTestsLiveData.value = TestsViewState.Loading
        firebaseProvider.observeTests()
            .onEach {
                allTestsLiveData.value =
                    if (it.isEmpty()) TestsViewState.EMPTY else TestsViewState.Value(it)
            }.launchIn(viewModelScope)
    }
}