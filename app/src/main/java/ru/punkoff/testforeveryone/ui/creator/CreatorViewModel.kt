package ru.punkoff.testforeveryone.ui.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class CreatorViewModel : ViewModel() {

    private val databaseHelper = LocalDatabase()

    fun saveTest(title: String, description: String) {
        viewModelScope.launch {
            val test = TestEntity(title, description)
            databaseHelper.saveTest(test)
        }
    }
}