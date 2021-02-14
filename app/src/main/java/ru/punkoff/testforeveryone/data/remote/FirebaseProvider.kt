package ru.punkoff.testforeveryone.data.remote

import ru.punkoff.testforeveryone.data.local.room.TestEntity

interface FirebaseProvider {
    suspend fun pushTest(test: TestEntity)
}