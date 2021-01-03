package ru.punkoff.testforeveryone.ui.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class CreatorViewModel : ViewModel() {

    fun createTest(title: String, description: String) {
        Repository.createTest(title, description)
    }

}