package ru.punkoff.testforeveryone.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TestEntity::class, ResultEntity::class], version = 1, exportSchema = false)
@TypeConverters(QuestionTypeConverter::class, ResultTypeConverter::class, ColorTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao
}