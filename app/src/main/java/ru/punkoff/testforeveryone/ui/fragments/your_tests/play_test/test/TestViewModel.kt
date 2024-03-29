package ru.punkoff.testforeveryone.ui.fragments.your_tests.play_test.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.utils.DateHelper
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class TestViewModel(val test: TestEntity?) : ViewModel() {

    private val mScore: MutableLiveData<HashMap<String, Int>> = MutableLiveData()
    private val testLiveData = MutableLiveData<TestEntity?>()
    private val resultLiveData = MutableLiveData<TempResult>()

    init {
        testLiveData.value = test
        mScore.value = HashMap()
        resultLiveData.value = TempResult()
    }

    fun observeViewState() = testLiveData

    fun refreshScore(answer: String, score: Int) {
        mScore.value?.put(answer, score)
        Log.d(javaClass.simpleName, mScore.value.toString())
    }

    fun createResult(test: TestEntity, score: Int, lastPlayText: String) {
        Log.d(javaClass.simpleName, "test.results: ${test.results}")
        if (test.results.isEmpty()) {
            createEmptyResult(test, score, lastPlayText)
        }
        test.results.forEach {
            var minScore = 0
            var maxScore = 0
            try {
                minScore = it.from.toInt()
            } catch (exc: NumberFormatException) {
                Log.e(javaClass.simpleName, exc.stackTraceToString())
            }
            try {
                maxScore = it.to.toInt()
            } catch (exc: NumberFormatException) {
                Log.e(javaClass.simpleName, exc.stackTraceToString())
            }
            Log.d(javaClass.simpleName, "SCORE: $score minScore: $minScore maxScore: $maxScore")
            if (score in minScore..maxScore) {
                resultLiveData.value?.createResult(
                    testTitle = test.title,
                    it.title,
                    it.description,
                    test.maxScore,
                    score,
                    lastPlayText + DateHelper.parseDate(),
                    test.color
                )
            }
        }
    }

    private fun createEmptyResult(test: TestEntity, score: Int, lastPlayText: String) {
        resultLiveData.value?.createResult(
            testTitle = test.title,
            "",
            "",
            test.maxScore,
            score,
            lastPlayText + DateHelper.parseDate(),
            test.color
        )
    }

    fun clearScore() {
        mScore.value?.clear()
    }

    fun getResultLiveData() = resultLiveData
    fun getScore(): LiveData<HashMap<String, Int>> = mScore
}