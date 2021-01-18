package ru.punkoff.testforeveryone.ui.creator.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.data.TempTest.Companion.EXTRA_TEMP_TEST
import ru.punkoff.testforeveryone.databinding.FragmentCreateQuestionsBinding

class CreateQuestionsFragment : Fragment() {
    private val test: TempTest? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            EXTRA_TEMP_TEST
        )
    }
    private val createQuestionsViewModel by viewModel<CreateQuestionsViewModel> {
        parametersOf(test)
    }

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
                createQuestionsViewModel.setMaxScore()

                createQuestionsViewModel.getTestLiveData().observe(viewLifecycleOwner) {
                    navigateToNextStepResult(it)
                }
            }
        }
    }

    private fun navigateToNextStepResult(test: TempTest) {
        (requireActivity() as MainActivity).navigateToNextStepResult(test)
    }
}