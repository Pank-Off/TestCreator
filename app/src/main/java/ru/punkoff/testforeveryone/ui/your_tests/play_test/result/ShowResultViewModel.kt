package ru.punkoff.testforeveryone.ui.your_tests.play_test.result

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.local.DatabaseProvider
import ru.punkoff.testforeveryone.data.local.room.TestEntity

class ShowResultViewModel(
    private val databaseHelper: DatabaseProvider,
    private val result: TempResult?
) : ViewModel() {

    private val testLiveData = MutableLiveData<TestEntity>()
    fun saveResult() {
        viewModelScope.launch {
            result?.let { databaseHelper.saveResult(result.getResult()) }
            Log.d(javaClass.simpleName, result?.getResult().toString())
        }
    }

    fun setOnShareBtnClickListener(shareText: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val shareSub = "Share sub"
        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
        intent.putExtra(Intent.EXTRA_TEXT, shareText)
        return intent
    }

    fun getTestByTitle(title: String) {
        viewModelScope.launch {
            val test = databaseHelper.getTestByTitle(title)
            testLiveData.value = test
        }
    }

    fun observeTest() = testLiveData
}