package ru.punkoff.testforeveryone.data

import android.util.Log
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.model.Question
import ru.punkoff.testforeveryone.model.Result

object Repository {
    var test = TestEntity(0, "", "", emptyList(), emptyList(), 0)

    var result = ResultEntity(0, "", "", "", 0, 0)
    val tests = listOf(
        TestEntity(
            0,
            "TITLE 1",
            "BODY",
            emptyList(), emptyList(), 0
        ),
        TestEntity(
            0,
            "TITLE 2",
            "BODY",
            emptyList(), emptyList(), 0
        ),
        TestEntity(
            0,
            "TITLE 3",
            "BODY",
            emptyList(), emptyList(), 0
        ),
        TestEntity(
            0,
            "TITLE 4",
            "BODY",
            emptyList(), emptyList(), 0
        ),
        TestEntity(
            0,
            "TITLE 5",
            "BODY",
            emptyList(), emptyList(), 0
        )
    )

    fun createTest(title: String, body: String) {
        test = TestEntity(0, title, body, emptyList(), emptyList(), 0)
    }

    fun setQuestions(questions: List<Question>) {
        test.questions = questions
        for (score in questions) {
            score.answers.forEach {
                Log.d("setQuestions: ", it.toString())
            }
        }
        Log.d(javaClass.simpleName, test.toString())
    }

    fun setResults(results: List<Result>) {
        test.results = results
        Log.d(javaClass.simpleName, test.toString())
    }

    fun setMaxScore(score: Int) {
        test.maxScore = score
    }

    fun createNewTest() {
        test = TestEntity(0, "", "", emptyList(), emptyList(), 0)
    }

    fun createResult(testTitle: String, title: String, body: String, maxScore: Int, score: Int) {
        result = ResultEntity(0, testTitle, title, body, maxScore, score)
    }

}