package ru.punkoff.testforeveryone.ui.your_tests.pass_test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class TestViewModel(val test: TestEntity?) : ViewModel() {

    private val mScore: MutableLiveData<HashMap<String, Int>> = MutableLiveData()
    private val testLiveData = MutableLiveData<TestEntity?>()

    init {
        testLiveData.value = test
        mScore.value = HashMap()
    }

    fun observeViewState() = testLiveData

    fun refreshScore(answer: String, score: Int) {
        mScore.value?.put(answer, score)
        Log.d(javaClass.simpleName, mScore.value.toString())
    }

    fun createResult(test: TestEntity?, score: Int) {

        //  Repository.setScore(score)

        //Repository.createResult(test?.title)
    }

    fun getScore(): LiveData<HashMap<String, Int>> = mScore

}