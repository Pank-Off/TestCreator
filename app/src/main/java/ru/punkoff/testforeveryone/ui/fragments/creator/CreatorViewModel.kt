package ru.punkoff.testforeveryone.ui.fragments.creator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.data.TempTest

class CreatorViewModel : ViewModel() {
    private val testLiveData = MutableLiveData<TempTest>()

    init {
        testLiveData.value = TempTest()
    }

    fun createTest(title: String, description: String) {
        testLiveData.value?.createTest(title, description)
    }

    fun getTest() = testLiveData
}