package ru.punkoff.testforeveryone.ui.fragments.creator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.data.TempTest

class CreatorViewModel : ViewModel() {
    private var test = TempTest()

    private val createTestState = MutableLiveData<CreateTestState>()

    init {
        createTestState.value = CreateTestState.StartScreen
        test = TempTest()
    }

    fun createTest(title: String, description: String) {
        test.createTest(title, description)
    }

    fun observeState() = createTestState

    fun setState(state: CreateTestState) {
        createTestState.value = state
    }

    fun getTest() = test
}