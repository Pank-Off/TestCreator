package ru.punkoff.testforeveryone.ui.your_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class YourResultsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is your results Fragment"
    }
    val text: LiveData<String> = _text
}