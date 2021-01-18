package ru.punkoff.testforeveryone.ui.creator

import androidx.lifecycle.ViewModel
import ru.punkoff.testforeveryone.data.Repository

class CreatorViewModel : ViewModel() {

    fun createTest(title: String, description: String) {
        Repository.createTest(title, description)
    }
}