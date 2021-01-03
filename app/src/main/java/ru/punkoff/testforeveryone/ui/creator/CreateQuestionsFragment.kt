package ru.punkoff.testforeveryone.ui.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.databinding.FragmentCreateQuestionsBinding
import ru.punkoff.testforeveryone.databinding.FragmentCreatorBinding

class CreateQuestionsFragment : Fragment() {

    private lateinit var createQuestionsViewModel: CreateQuestionsViewModel

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

        with(binding) {
            nextBtn.setOnClickListener {
                navigateToNextStepResult()
            }
        }
    }

    private fun navigateToNextStepResult() {
        (requireActivity() as MainActivity).navigateToNextStepResult()
    }
}