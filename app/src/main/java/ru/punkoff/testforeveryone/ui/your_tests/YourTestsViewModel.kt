package ru.punkoff.testforeveryone.ui.your_tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class YourTestsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is your tests Fragment"
    }
    val text: LiveData<String> = _text
}