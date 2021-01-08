package ru.punkoff.testforeveryone.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.punkoff.testforeveryone.model.Question

class QuestionTypeConverter {

    @TypeConverter
    fun questionsToJson(questions: List<Question>): String = Gson().toJson(questions)

    @TypeConverter
    fun jsonToQuestions(questions: String) =
        Gson().fromJson(questions, Array<Question>::class.java).toList()
}

