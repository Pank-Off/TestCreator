package ru.punkoff.testforeveryone.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.errors.NoAuthException

class SplashViewModel(private val user: FirebaseUser?) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<SplashViewState>()

    init {
        viewModelScope.launch {
            requestUser()
        }
    }

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData
    private fun requestUser() {
        viewStateLiveData.value =
            user?.let { SplashViewState.Auth } ?: SplashViewState.Error(error = NoAuthException())
    }
}