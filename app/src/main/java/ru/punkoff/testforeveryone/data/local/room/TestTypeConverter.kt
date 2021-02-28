package ru.punkoff.testforeveryone.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.punkoff.testforeveryone.model.TypeTest

class TestTypeConverter {
    @TypeConverter
    fun typeTestToJson(typeTest: TypeTest): String = Gson().toJson(typeTest)

    @TypeConverter
    fun jsonToTypeTest(typeTest: String): TypeTest {
        val type = object : TypeToken<TypeTest>() {}.type
        return Gson().fromJson(typeTest, type)
    }
}