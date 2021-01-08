package ru.punkoff.testforeveryone.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.punkoff.testforeveryone.model.Result

class ResultTypeConverter {
    @TypeConverter
    fun resultsToJson(results: List<Result>): String = Gson().toJson(results)

    @TypeConverter
    fun jsonToResults(results: String) =
        Gson().fromJson(results, Array<Result>::class.java).toList()
}