package ru.punkoff.testforeveryone.ui.fragments.creator.questions

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils.loadAnimation
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fragment_create_questions.*
import kotlinx.android.synthetic.main.item_fragment_questions_choice.view.*
import kotlinx.android.synthetic.main.item_fragment_questions_score.view.textEditTextAnswerOne
import kotlinx.android.synthetic.main.item_fragment_questions_score.view.textEditTextAnswerThree
import kotlinx.android.synthetic.main.item_fragment_questions_score.view.textEditTextAnswerTwo
import kotlinx.android.synthetic.main.item_fragment_questions_score.view.textEditTextQuestion
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.data.TempTest.Companion.EXTRA_TEMP_TEST
import ru.punkoff.testforeveryone.databinding.FragmentCreateQuestionsBinding
import ru.punkoff.testforeveryone.model.TypeTest
import ru.punkoff.testforeveryone.ui.activities.MainActivity
import ru.punkoff.testforeveryone.utils.hideKeyboard


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
    private var fabIsNotExpanded = true
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

        setHasOptionsMenu(true)
        createQuestionsViewModel.clearQuestionsList()
        if (count == 0) {
            addQuestionFragmentToContainer()
        }
        with(binding) {
            mainFab.startAnimation(loadAnimation(context, R.anim.enlarge_main_fab))
            mainFab.setOnClickListener {
                fabIsNotExpanded = if (fabIsNotExpanded) {
                    mainFab.showFabs(delete_fab, add_fab, next_step_fab)
                    setOnDeleteFabClickListener()
                    setOnAddFabClickListener()
                    setOnNextStepFabClickListener()
                    false
                } else {
                    mainFab.hideFabs(delete_fab, add_fab, next_step_fab)
                    true
                }
            }
        }
    }

    private fun setOnDeleteFabClickListener() {
        delete_fab.setOnClickListener {
            if (count!! > 1) {
                detachQuestionFragmentFromContainer()
            }
        }
    }

    private fun setOnAddFabClickListener() {
        add_fab.setOnClickListener {
            addQuestionFragmentToContainer()
            main_fab.hideFabs(delete_fab, add_fab, next_step_fab)
            fabIsNotExpanded = true
        }
    }

    private fun setOnNextStepFabClickListener() {
        next_step_fab.setOnClickListener {
            var emptyField = false
            for (i in 1..count!!) {
                val frag =
                    childFragmentManager.findFragmentByTag("FragQuestion $i") as ItemFragment
                val emptyQuestionField = frag.view?.textEditTextQuestion?.text.toString() == ""
                val emptyTwoAnswersField =
                    (frag.view?.textEditTextAnswerOne?.text.toString() == "" && frag.view?.textEditTextAnswerTwo?.text.toString() == "") ||
                            (frag.view?.textEditTextAnswerTwo?.text.toString() == "" && frag.view?.textEditTextAnswerThree?.text.toString() == "") ||
                            (frag.view?.textEditTextAnswerOne?.text.toString() == "" && frag.view?.textEditTextAnswerThree?.text.toString() == "") ||
                            (frag.view?.textEditTextAnswerOne?.text.toString() == "" && frag.view?.textEditTextAnswerTwo?.text.toString() == "" &&
                                    frag.view?.textEditTextAnswerThree?.text.toString() == "")

                var notSelectedCorrectAnswer = false
                if (frag is QuestionsAnswerChoiceFragment) {
                    notSelectedCorrectAnswer =
                        (!frag.view?.radioBtnOne!!.isChecked && !frag.view?.radioBtnTwo!!.isChecked && !frag.view?.radioBtnThree!!.isChecked)
                }
                when {
                    emptyQuestionField -> {
                        frag.view?.textEditTextQuestion?.error = getString(R.string.input_question)
                        showSnackBar(resources.getString(R.string.empty_question))
                        emptyField = true
                    }
                    emptyTwoAnswersField -> {
                        showSnackBar(resources.getString(R.string.input_question_text_snackbar))
                        emptyField = true
                    }
                    notSelectedCorrectAnswer -> {
                        showSnackBar(resources.getString(R.string.choose_correct_answer))
                        emptyField = true
                    }
                    else -> {
                        createQuestionsViewModel.setQuestions(
                            frag, frag.view?.textEditTextQuestion?.text.toString(),
                            frag.view?.textEditTextAnswerOne?.text.toString(),
                            frag.view?.textEditTextAnswerTwo?.text.toString(),
                            frag.view?.textEditTextAnswerThree?.text.toString()
                        )
                    }
                }
            }
            if (!emptyField) {
                createQuestionsViewModel.setMaxScore()
                createQuestionsViewModel.getTestLiveData().observe(viewLifecycleOwner) { test ->
                    navigateToNextStepResult(test)
                }
            }
            main_fab.hideFabs(delete_fab, add_fab, next_step_fab)
            fabIsNotExpanded = true
        }
    }

    private fun addQuestionFragmentToContainer() {
        var fragment = Fragment()
        when {
            test?.getType() == TypeTest.AnswerChoiceTest -> fragment =
                QuestionsAnswerChoiceFragment()
            test?.getType() == TypeTest.SetScoreTest -> fragment = QuestionsSetScoreFragment()
        }
        count = childFragmentManager.fragments.size + 1
        childFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment, "FragQuestion $count").commit()
    }

    private fun detachQuestionFragmentFromContainer() {
        childFragmentManager.beginTransaction()
            .detach(fragmentContainer[count?.minus(1)!!].findFragment()).commit()
        count = childFragmentManager.fragments.size - 1
        main_fab.hideFabs(delete_fab, add_fab, next_step_fab)
        fabIsNotExpanded = true
    }

    private fun showSnackBar(snackBarText: String) {
        val snackBar = Snackbar.make(main_fab, snackBarText, Snackbar.LENGTH_LONG)
        snackBar.show()
        snackBar.anchorView = main_fab
        snackBar.show()
        createQuestionsViewModel.clearQuestionsList()
    }

    private fun navigateToNextStepResult(test: TempTest) {
        (requireActivity() as MainActivity).navigateToNextStepResult(test)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.search).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.help -> context?.let {
                MaterialAlertDialogBuilder(it).setView(R.layout.help_dialog_fragment_questions_layout)
                    .show()
            }
            android.R.id.home -> findNavController().popBackStack()
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(activity)
    }
}
