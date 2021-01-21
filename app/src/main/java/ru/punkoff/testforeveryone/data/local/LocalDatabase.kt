package ru.punkoff.testforeveryone.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.punkoff.testforeveryone.app.App
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.data.local.room.TestDao
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class LocalDatabase : DatabaseProvider {

    private var testDao: TestDao = App.getAppDatabase().testDao()

    override suspend fun observeTests(): List<TestEntity> =
        withContext(Dispatchers.IO) { testDao.getTests() }

    override suspend fun saveTest(test: TestEntity) =
        withContext(Dispatchers.IO) { testDao.insert(test) }

    override suspend fun saveResult(result: ResultEntity) {
        withContext(Dispatchers.IO) { testDao.insert(result) }
    }

    override suspend fun observeResults(): List<ResultEntity> =
        withContext(Dispatchers.IO) { testDao.getResults() }

    override suspend fun getTestByTitle(titleTest: String): TestEntity =
        withContext(Dispatchers.IO) { testDao.getTestByTitle(titleTest) }

    override suspend fun deleteTest(id: Long) =
        withContext(Dispatchers.IO) { testDao.deleteTest(id) }

    override suspend fun deleteResult(id: Long) {
        withContext(Dispatchers.IO) { testDao.deleteResult(id) }
    }
}