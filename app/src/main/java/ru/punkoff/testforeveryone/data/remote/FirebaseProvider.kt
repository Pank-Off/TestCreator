package ru.punkoff.testforeveryone.data.remote

import kotlinx.coroutines.flow.Flow
import ru.punkoff.testforeveryone.data.local.room.TestEntity

interface FirebaseProvider {
    suspend fun pushTest(test: TestEntity)
    fun observeTests(): Flow<List<TestEntity>>
}