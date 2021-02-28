package ru.punkoff.testforeveryone.ui.fragments.creator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.model.TypeTest

class CreatorViewModel : ViewModel() {
    private var test = TempTest()

    private lateinit var typeTest: TypeTest
    private val createTestState = MutableLiveData<CreateTestState>()

    init {
        createTestState.value = CreateTestState.StartScreen
        test = TempTest()
    }

    fun createTest(title: String, description: String) {
        test.createTest(title, description, typeTest)
    }

    fun observeState() = createTestState

    fun setState(state: CreateTestState) {
        createTestState.value = state
    }

    fun setType(type: TypeTest) {
        typeTest = type
    }

    fun getTest() = test
}