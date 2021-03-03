package ru.punkoff.testforeveryone.app

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.punkoff.testforeveryone.data.local.room.AppDatabase
import ru.punkoff.testforeveryone.di.DependencyGraph

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(DependencyGraph.modules)
        }
    }
}