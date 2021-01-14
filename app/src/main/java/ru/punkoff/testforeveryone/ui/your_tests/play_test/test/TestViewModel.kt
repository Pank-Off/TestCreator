package ru.punkoff.testforeveryone.ui.your_tests.play_test.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class TestViewModel(val test: TestEntity?) : ViewModel() {

    private val databaseHelper = LocalDatabase()
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

    fun createResult(test: TestEntity, score: Int) {
        test.results.forEach {
            if (score >= it.from.toInt() && score <= it.to.toInt()) {
                Repository.createResult(it.title, it.description, test.maxScore, score)
            }
        }
    }

    fun saveResult() {
        viewModelScope.launch {
            databaseHelper.saveResult(Repository.result)
            Log.d(javaClass.simpleName, Repository.result.toString())
        }
    }

    fun getScore(): LiveData<HashMap<String, Int>> = mScore

}