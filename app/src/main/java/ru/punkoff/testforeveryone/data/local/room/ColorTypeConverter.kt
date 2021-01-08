package ru.punkoff.testforeveryone.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ColorTypeConverter {

    @TypeConverter
    fun colorToJson(color: Color): String = Gson().toJson(color)

    @TypeConverter
    fun jsonToColor(color: String): Color {
        val type = object : TypeToken<Color>() {}.type
        return Gson().fromJson(color, type)
    }
}