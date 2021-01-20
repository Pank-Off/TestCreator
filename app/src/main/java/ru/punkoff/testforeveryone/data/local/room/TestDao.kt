package ru.punkoff.testforeveryone.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TestDao {

    @Insert
    fun insert(test: TestEntity)

    @Query("DELETE FROM table_tests WHERE testId = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM table_tests")
    fun getTests(): List<TestEntity>

    @Insert
    fun insert(result: ResultEntity)

    @Query("SELECT * FROM table_results")
    fun getResults(): List<ResultEntity>

    @Query("SELECT * FROM table_tests WHERE title = :titleTest")
    fun getTestByTitle(titleTest: String): TestEntity
}