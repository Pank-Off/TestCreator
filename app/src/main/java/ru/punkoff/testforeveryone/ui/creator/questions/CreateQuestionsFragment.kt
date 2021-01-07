package ru.punkoff.testforeveryone.ui.creator.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.databinding.FragmentCreateQuestionsBinding

class CreateQuestionsFragment : Fragment() {

    private lateinit var createQuestionsViewModel: CreateQuestionsViewModel

    private lateinit var fragContainer: LinearLayout
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

        fragContainer = view.findViewById(R.id.fragmentContainer)

        with(binding) {
            addQuestionBtn.setOnClickListener {
                val fragment = QuestionsFragment()
                val linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.VERTICAL
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.fragmentContainer, fragment)?.commit()
                fragmentContainer.addView(linearLayout)
            }
            nextBtn.setOnClickListener {
                navigateToNextStepResult()
            }
        }
    }

    private fun navigateToNextStepResult() {
        (requireActivity() as MainActivity).navigateToNextStepResult()
    }
}