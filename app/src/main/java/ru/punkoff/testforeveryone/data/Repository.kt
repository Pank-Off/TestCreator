package ru.punkoff.testforeveryone.data

import android.util.Log
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.model.Question
import ru.punkoff.testforeveryone.model.Result

object Repository {
    var test = TestEntity(0, "", "", emptyList(), emptyList())

    //  var results = ResultEntity(0, "", "", 0, 0)
    val tests = listOf(
        TestEntity(
            0,
            "TITLE 1",
            "BODY",
            emptyList(), emptyList()
        ),
        TestEntity(
            0,
            "TITLE 2",
            "BODY",
            emptyList(), emptyList()
        ),
        TestEntity(
            0,
            "TITLE 3",
            "BODY",
            emptyList(), emptyList()
        ),
        TestEntity(
            0,
            "TITLE 4",
            "BODY",
            emptyList(), emptyList()
        ),
        TestEntity(
            0,
            "TITLE 5",
            "BODY",
            emptyList(), emptyList()
        )
    )

    fun createTest(title: String, body: String) {
        test = TestEntity(0, title, body, emptyList(), emptyList())
    }

    fun setQuestions(questions: List<Question>) {
        test.questions = questions
        Log.d(javaClass.simpleName, test.toString())
    }

    fun setResults(results: List<Result>) {
        test.results = results
        Log.d(javaClass.simpleName, test.toString())
    }

    fun createNewTest() {
        test = TestEntity(0, "", "", emptyList(), emptyList())
    }
}