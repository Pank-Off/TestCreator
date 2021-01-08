package ru.punkoff.testforeveryone.ui.creator.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.item_fragment_questions.view.*
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.model.Question

class CreateQuestionsViewModel : ViewModel() {

    private val questionsList = mutableListOf<Question>()
    private val questionsLiveData = MutableLiveData<List<Question>>()

    fun setQuestions(frag: QuestionsFragment) {
        questionsList.add(
            Question(
                frag.view?.textEditTextQuestion?.text.toString(),
                frag.view?.textEditTextAnswerOne?.text.toString(),
                frag.view?.textEditTextAnswerTwo?.text.toString(),
                frag.view?.textEditTextAnswerThree?.text.toString()
            )
        )
        questionsLiveData.value = questionsList
        Repository.setQuestions(questionsList)
    }

    override fun onCleared() {
        super.onCleared()
        questionsLiveData.value = emptyList()
    }
}