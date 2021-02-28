package ru.punkoff.testforeveryone.ui.fragments.creator.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.punkoff.testforeveryone.R

class QuestionsSetScoreFragment : ItemFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_fragment_questions_score, container, false)
    }
}