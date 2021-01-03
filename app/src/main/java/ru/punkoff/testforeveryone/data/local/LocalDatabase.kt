package ru.punkoff.testforeveryone.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.punkoff.testforeveryone.app.App
import ru.punkoff.testforeveryone.data.local.room.TestDao
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class LocalDatabase : DatabaseProvider {

    private var testDao: TestDao = App.getAppDatabase().testDao()

    override suspend fun observeTests(): List<TestEntity> =
        withContext(Dispatchers.IO) { testDao.getTests() }

    override suspend fun saveTest(test: TestEntity) =
        withContext(Dispatchers.IO) { testDao.insert(test) }

}