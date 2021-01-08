package ru.punkoff.testforeveryone.ui.creator.questions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.databinding.FragmentCreateQuestionsBinding

class CreateQuestionsFragment : Fragment() {

    private lateinit var createQuestionsViewModel: CreateQuestionsViewModel

    private var count: Int? = 0
    private var _binding: FragmentCreateQuestionsBinding? = null
    private val binding: FragmentCreateQuestionsBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createQuestionsViewModel =
            ViewModelProvider(this).get(CreateQuestionsViewModel::class.java)
        Log.d(javaClass.simpleName, "Pochemu ${Repository.test}")
        with(binding) {
            addQuestionBtn.setOnClickListener {
                val fragment = QuestionsFragment()
                count = childFragmentManager.fragments.size + 1
                childFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, "FragQuestion $count").commit()
            }
            nextBtn.setOnClickListener {
                for (i in 1..count!!) {
                    val frag =
                        childFragmentManager.findFragmentByTag("FragQuestion $i") as QuestionsFragment
                    createQuestionsViewModel.setQuestions(frag)
                }
                navigateToNextStepResult()
            }
        }
    }

    private fun navigateToNextStepResult() {
        (requireActivity() as MainActivity).navigateToNextStepResult()
    }
}