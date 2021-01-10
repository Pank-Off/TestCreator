package ru.punkoff.testforeveryone.ui.your_tests.pass_test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class TestViewModel(val test: TestEntity?) : ViewModel() {

    private val testLiveData = MutableLiveData<TestEntity?>()

    init {
        testLiveData.value = test
    }

    fun observeViewState() = testLiveData

}