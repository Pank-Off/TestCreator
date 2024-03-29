package ru.punkoff.testforeveryone.ui.fragments.creator.questions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.item_fragment_questions_choice.view.*
import kotlinx.android.synthetic.main.item_fragment_questions_score.view.*
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.model.Question

class CreateQuestionsViewModel(test: TempTest) : ViewModel() {

    private val questionsList = mutableListOf<Question>()
    private val scoreList = mutableListOf<Int?>()
    private val questionsLiveData = MutableLiveData<List<Question>>()

    private val testLiveData = MutableLiveData<TempTest>()

    init {
        testLiveData.value = test
    }

    fun setQuestions(
        frag: ItemFragment,
        question: String,
        answerOne: String,
        answerTwo: String,
        answerThree: String
    ) {
        val hashMap = LinkedHashMap<String, String?>()

        when (frag) {
            is QuestionsSetScoreFragment -> {
                hashMap[answerOne] =
                    if (frag.view?.textEditTextRateOne?.text.toString() == "") "0" else frag.view?.textEditTextRateOne?.text.toString()

                hashMap[answerTwo] =
                    if (frag.view?.textEditTextRateTwo?.text.toString() == "") "0" else frag.view?.textEditTextRateTwo?.text.toString()

                hashMap[answerThree] =
                    if (frag.view?.textEditTextRateThree?.text.toString() == "") "0" else frag.view?.textEditTextRateThree?.text.toString()
            }
            is QuestionsAnswerChoiceFragment -> {
                hashMap[answerOne] =
                    if (frag.view?.radioBtnOne!!.isChecked) "1" else "0"

                hashMap[answerTwo] =
                    if (frag.view?.radioBtnTwo!!.isChecked) "1" else "0"

                hashMap[answerThree] =
                    if (frag.view?.radioBtnThree!!.isChecked) "1" else "0"

            }
        }
        if (hashMap.containsKey("")) {
            hashMap.remove("")
        }

        questionsList.add(
            Question(
                question,
                hashMap
            )
        )

        val list = mutableListOf<Int>()
        hashMap.forEach {
            it.value?.toInt()?.let { it1 -> list.add(it1) }
        }
        val int = list.maxOrNull()
        scoreList.add(int)
        Log.d(javaClass.simpleName, hashMap.toString())
        testLiveData.value?.setQuestions(questionsList)
        questionsLiveData.value = questionsList
    }

    fun setMaxScore() {
        var maxScore = 0
        scoreList.forEach {
            if (it != null) {
                maxScore += it
            }
        }
        testLiveData.value?.setMaxScore(maxScore)
    }

    fun clearQuestionsList() {
        questionsList.clear()
        questionsLiveData.value = questionsList
        scoreList.clear()
    }

    fun getTestLiveData() = testLiveData
}