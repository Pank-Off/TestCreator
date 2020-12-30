package ru.punkoff.testforeveryone.model

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import kotlinx.android.parcel.Parcelize
import ru.punkoff.testforeveryone.R

@Parcelize
data class Test(
    val title: String,
    val body: String,
    var color: Color = Color.values().toList().shuffled().first()
) : Parcelable


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