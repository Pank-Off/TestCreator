package ru.punkoff.testforeveryone.data.local

import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.data.local.room.TestEntity

interface DatabaseProvider {
    suspend fun observeTests(): List<TestEntity>

    suspend fun saveTest(test: TestEntity)

    suspend fun saveResult(result: ResultEntity)

    suspend fun observeResults(): List<ResultEntity>

    suspend fun getTestByTitle(titleTest: String): TestEntity
}