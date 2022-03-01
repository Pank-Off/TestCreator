package ru.punkoff.testforeveryone.ui.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.punkoff.testforeveryone.utils.ByLazyDelegate

class MainViewModel(private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()) : ViewModel() {
    private val currentUserLiveData = MutableLiveData<FirebaseUser>()
    private val currentUser by ByLazyDelegate {
        Log.e(javaClass.simpleName, "First start")
        mAuth.currentUser
    }

    init {
        currentUserLiveData.value = currentUser
        Log.e(javaClass.simpleName, currentUser.toString())
    }

    fun observeCurrentUser(): LiveData<FirebaseUser> = currentUserLiveData
}
