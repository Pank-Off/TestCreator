package ru.punkoff.testforeveryone.data.local

import ru.punkoff.testforeveryone.data.local.room.TestEntity

interface DatabaseProvider {
    suspend fun observeTests(): List<TestEntity>

    suspend fun saveTest(test: TestEntity)
}