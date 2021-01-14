package ru.punkoff.testforeveryone.ui.creator.questions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.item_fragment_questions.view.*
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.model.Question

class CreateQuestionsViewModel : ViewModel() {

    private val questionsList = mutableListOf<Question>()
    private val questionsLiveData = MutableLiveData<List<Question>>()

    fun setQuestions(frag: QuestionsFragment) {
        val hashMap = LinkedHashMap<String, String?>()

        hashMap[frag.view?.textEditTextAnswerOne?.text.toString()] =
            if (frag.view?.textEditTextRateOne?.text.toString() == "") "0" else frag.view?.textEditTextRateOne?.text.toString()

        hashMap[frag.view?.textEditTextAnswerTwo?.text.toString()] =
            if (frag.view?.textEditTextRateTwo?.text.toString() == "") "0" else frag.view?.textEditTextRateTwo?.text.toString()

        hashMap[frag.view?.textEditTextAnswerThree?.text.toString()] =
            if (frag.view?.textEditTextRateThree?.text.toString() == "") "0" else frag.view?.textEditTextRateThree?.text.toString()

        if (hashMap.containsKey("")) {
            hashMap.remove("")
        }
        Log.d(javaClass.simpleName, hashMap.toString())
        questionsList.add(Question(frag.view?.textEditTextQuestion?.text.toString(), hashMap))
        questionsLiveData.value = questionsList
        Repository.setQuestions(questionsList)

    }

    override fun onCleared() {
        super.onCleared()
        questionsLiveData.value = emptyList()
    }
}