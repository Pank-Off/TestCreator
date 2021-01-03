package ru.punkoff.testforeveryone.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.punkoff.testforeveryone.model.Test

@Dao
interface TestDao {

    @Insert
    fun insert(test: TestEntity)

    @Query("SELECT * FROM table_tests")
    fun getTests(): List<TestEntity>
}