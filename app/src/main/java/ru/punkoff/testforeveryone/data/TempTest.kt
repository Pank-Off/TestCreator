package ru.punkoff.testforeveryone.data

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.model.Question
import ru.punkoff.testforeveryone.model.Result
import ru.punkoff.testforeveryone.model.TypeTest

@Parcelize
class TempTest : Parcelable {

    @IgnoredOnParcel
    private var test =
        TestEntity(0, "", "", emptyList(), emptyList(), 0, "", TypeTest.AnswerChoiceTest)

    fun createTest(title: String, body: String, typeTest: TypeTest) {
        test =
            TestEntity(0, title, body, emptyList(), emptyList(), 0, "", typeTest)
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

    fun getTest() = test

    fun getMaxScore() = test.maxScore

    fun getType() = test.type
    fun setDataCreate(date: String) {
        test.createData = date
    }

    companion object {
        const val EXTRA_TEMP_TEST = "TEST"
    }
}