package ru.punkoff.testforeveryone.ui.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.data.local.LocalDatabase

class CreateResultsViewModel : ViewModel() {

    private val databaseHelper = LocalDatabase()

    fun saveTest() {
        viewModelScope.launch {
            databaseHelper.saveTest(Repository.test)
        }
    }
}