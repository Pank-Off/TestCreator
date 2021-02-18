package ru.punkoff.testforeveryone.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainViewModel(private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()) : ViewModel() {
    private val currentUserLiveData = MutableLiveData<FirebaseUser>()
    private val currentUser
        get() = mAuth.currentUser

    init {
        currentUserLiveData.value = currentUser
    }

    fun observeCurrentUser(): LiveData<FirebaseUser> = currentUserLiveData
}