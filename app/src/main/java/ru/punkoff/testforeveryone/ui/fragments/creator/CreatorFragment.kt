package ru.punkoff.testforeveryone.ui.fragments.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_questions.*
import kotlinx.android.synthetic.main.fragment_creator.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.databinding.FragmentCreatorBinding
import ru.punkoff.testforeveryone.ui.activities.MainActivity
import ru.punkoff.testforeveryone.utils.hideKeyboard

class CreatorFragment : Fragment() {

    private val creatorViewModel by viewModel<CreatorViewModel>()

    private var _binding: FragmentCreatorBinding? = null
    private val binding: FragmentCreatorBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            creatorViewModel.observeState().observe(viewLifecycleOwner) { state ->
                when (state) {
                    CreateTestState.StartScreen -> {
                    }
                    CreateTestState.EmptyTitle -> {
                        textInputTitle.error = getString(R.string.input_title)
                    }
                    CreateTestState.MaxCountTitleError -> {
                        textFieldTitle.helperText = getString(R.string.max_length)
                    }
                    CreateTestState.EmptyDescription -> {
                        textInputDescription.error = getString(R.string.input_description)
                    }
                    CreateTestState.MaxCountDescriptionError -> {
                        textFieldDescription.helperText = getString(R.string.max_length)
                    }
                    CreateTestState.ErrorRadioButton -> showSnackBar(getString(R.string.select_the_test_type))
                    CreateTestState.SuccessCreate -> {
                        creatorViewModel.setState(CreateTestState.StartScreen)
                        creatorViewModel.createTest(
                            textInputTitle.text.toString(),
                            textInputDescription.text.toString()
                        )
                        val test = creatorViewModel.getTest()
                        navigateToNextStep(test)
                        nextBtn.startAnimation(
                            loadAnimation(
                                context,
                                R.anim.shrink_main_fab
                            )
                        )
                    }
                }
            }

            choiceAnswerRadioBtn.setOnClickListener {
                choiceAnswerRadioBtn.isChecked = true
                setScoreRadioBtn.isChecked = false
            }
            setScoreRadioBtn.setOnClickListener {
                choiceAnswerRadioBtn.isChecked = false
                setScoreRadioBtn.isChecked = true
            }
            nextBtn.startAnimation(loadAnimation(context, R.anim.enlarge_main_fab))
            nextBtn.setOnClickListener {
                when {
                    textInputTitle.text.toString() == "" -> {
                        creatorViewModel.setState(CreateTestState.EmptyTitle)
                    }
                    textInputDescription.text.toString() == "" -> {
                        creatorViewModel.setState(CreateTestState.EmptyDescription)
                    }
                    textInputTitle.text?.length!! > textFieldTitle.counterMaxLength -> {
                        creatorViewModel.setState(CreateTestState.MaxCountTitleError)
                    }
                    textInputDescription.text?.length!! > textFieldDescription.counterMaxLength -> {
                        creatorViewModel.setState(CreateTestState.MaxCountDescriptionError)
                    }
                    !choiceAnswerRadioBtn.isChecked && !setScoreRadioBtn.isChecked -> creatorViewModel.setState(
                        CreateTestState.ErrorRadioButton
                    )
                    else -> creatorViewModel.setState(CreateTestState.SuccessCreate)
                }
            }
        }
    }

    private fun showSnackBar(snackBarText: String) {
        val snackBar = Snackbar.make(next_btn, snackBarText, Snackbar.LENGTH_LONG)
        snackBar.show()
        snackBar.anchorView = main_fab
        snackBar.show()
    }

    private fun navigateToNextStep(test: TempTest) {
        (requireActivity() as MainActivity).navigateToNextStep(test)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(activity)
    }
}