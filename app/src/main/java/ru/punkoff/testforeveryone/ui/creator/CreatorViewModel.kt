package ru.punkoff.testforeveryone.ui.creator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreatorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Creator Fragment"
    }
    val text: LiveData<String> = _text
}