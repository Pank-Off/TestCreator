package ru.punkoff.testforeveryone.ui.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.errors.NoAuthException

class SplashViewModel(private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<SplashViewState>()
    private val currentUser
        get() = mAuth.currentUser

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData
    fun requestUser() {
        viewModelScope.launch {
            viewStateLiveData.value =
                currentUser?.let { SplashViewState.Auth }
                    ?: SplashViewState.Error(error = NoAuthException())
        }
    }

    fun signInAnonymously() {
        viewModelScope.launch {
            mAuth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewStateLiveData.value = SplashViewState.Auth
                        Log.d(javaClass.simpleName, "signInAnonymously:success")
                    } else {
                        viewStateLiveData.value = SplashViewState.Error(error = NoAuthException())
                        Log.w(javaClass.simpleName, "signInAnonymously:failure", task.exception)
                    }
                }
        }
    }
}