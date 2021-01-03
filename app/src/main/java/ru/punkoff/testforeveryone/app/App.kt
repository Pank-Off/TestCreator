package ru.punkoff.testforeveryone.app

import android.app.Application
import androidx.room.Room
import ru.punkoff.testforeveryone.data.local.room.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appDatabase = generateAppDataBase()
    }

    private fun generateAppDataBase(): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "room_database"
        )
            //.allowMainThreadQueries()  //только для тестирования
            .build()
    }

    companion object {
        private lateinit var instance: App
        private lateinit var appDatabase: AppDatabase
        fun getAppDatabase(): AppDatabase = appDatabase
    }
}