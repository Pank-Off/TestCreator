package ru.punkoff.testforeveryone.data.local.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.model.Question
import ru.punkoff.testforeveryone.model.Result

@Entity(tableName = "table_tests")
@Parcelize
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String,
    var questions: List<Question>,
    var results: List<Result>,
    var maxScore: Int,
    var color: Color = Color.values().toList().shuffled().first(),
) : Parcelable


fun Color.mapToColor(): Int {

    return when (this) {
        Color.WHITE -> R.drawable.gradient_white
        Color.YELLOW -> R.drawable.gradient_yellow
        Color.GREEN -> R.drawable.gradient_green
        Color.BLUE -> R.drawable.gradient_blue
        Color.RED -> R.drawable.gradient_red
        Color.VIOLET -> R.drawable.gradient_violet
        Color.PINK -> R.drawable.gradient_pink
    }
}