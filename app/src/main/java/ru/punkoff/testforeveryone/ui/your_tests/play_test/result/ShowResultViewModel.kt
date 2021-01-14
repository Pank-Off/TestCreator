package ru.punkoff.testforeveryone.ui.your_tests.play_test.result

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.data.local.LocalDatabase

class ShowResultViewModel : ViewModel() {

    private val databaseHelper = LocalDatabase()
    fun saveResult() {
        viewModelScope.launch {
            databaseHelper.saveResult(Repository.result)
            Log.d(javaClass.simpleName, Repository.result.toString())
        }
    }

    fun setOnShareBtnClickListener(): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val shareBody = "ref on app in Google market"
        val shareSub = "Share sub"
        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        return intent
    }

}