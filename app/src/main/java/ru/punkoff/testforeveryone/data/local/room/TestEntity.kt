package ru.punkoff.testforeveryone.data.local.room

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.model.Question
import ru.punkoff.testforeveryone.model.Result

@Entity(tableName = "table_tests")

data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String,
    var questions: List<Question>,
    var results: List<Result>,
    var color: Color = Color.values().toList().shuffled().first(),
)

enum class Color {
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK
}

fun Color.mapToColor(context: Context): Int {

    val id = when (this) {

        Color.WHITE -> R.color.color_white
        Color.YELLOW -> R.color.color_yellow
        Color.GREEN -> R.color.color_green
        Color.BLUE -> R.color.color_blue
        Color.RED -> R.color.color_red
        Color.VIOLET -> R.color.color_violet
        Color.PINK -> R.color.color_pink
    }

    return ContextCompat.getColor(context, id)
}