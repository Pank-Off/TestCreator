package ru.punkoff.testforeveryone.ui.all_tests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllTestsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is all tests Fragment"
    }
    val text: LiveData<String> = _text
}