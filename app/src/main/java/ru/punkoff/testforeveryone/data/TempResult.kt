package ru.punkoff.testforeveryone.data

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import ru.punkoff.testforeveryone.data.local.room.Color
import ru.punkoff.testforeveryone.data.local.room.ResultEntity

@Parcelize
class TempResult : Parcelable {

    @IgnoredOnParcel
    private var result = ResultEntity(0, "", "", "", 0, 0, "")
    fun createResult(
        testTitle: String,
        title: String,
        body: String,
        maxScore: Int,
        score: Int,
        lastPlay: String,
        color: Color
    ) {
        Log.d(javaClass.simpleName, "Color: $color")
        result = ResultEntity(0, testTitle, title, body, maxScore, score, lastPlay, color)
    }

    fun getColor() = result.color

    fun getScore() = result.score

    fun getMaxScore() = result.maxScore

    fun getTitle() = result.title

    fun getBody() = result.body

    fun getResult() = result

    companion object {
        const val EXTRA_TEMP_RESULT = "EXTRA_TEMP_RESULT"
    }
}