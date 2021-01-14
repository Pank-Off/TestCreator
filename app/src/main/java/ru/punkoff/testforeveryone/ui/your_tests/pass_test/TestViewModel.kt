package ru.punkoff.testforeveryone.ui.your_tests.pass_test

import android.telecom.CallScreeningService
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class TestViewModel(val test: TestEntity?) : ViewModel() {

    private val mScore: MutableLiveData<HashMap<Int, Int?>> = MutableLiveData()
    private val testLiveData = MutableLiveData<TestEntity?>()

    init {
        testLiveData.value = test
        mScore.value = HashMap()
    }

    fun observeViewState() = testLiveData

    fun refreshScore(score: Int?, position: Int) {
        Log.d(javaClass.simpleName, mScore.value.toString())
        mScore.value?.put(position, score)
    }

    fun getScore(): LiveData<HashMap<Int, Int?>> = mScore
}