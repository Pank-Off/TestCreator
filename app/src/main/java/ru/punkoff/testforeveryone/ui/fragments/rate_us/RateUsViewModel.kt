package ru.punkoff.testforeveryone.ui.fragments.rate_us

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RateUsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is rate us Fragment"
    }
    val text: LiveData<String> = _text
}