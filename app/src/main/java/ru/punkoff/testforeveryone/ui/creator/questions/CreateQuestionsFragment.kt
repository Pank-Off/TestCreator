package ru.punkoff.testforeveryone.ui.creator.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fragment_create_questions.*
import kotlinx.android.synthetic.main.item_fragment_questions.view.*
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

        createQuestionsViewModel.clearQuestionsList()
        if (count == 0) {
            val fragment = QuestionsFragment()
            count = childFragmentManager.fragments.size + 1
            childFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, "FragQuestion $count").commit()
        }
        with(binding) {
            mainFab.startAnimation(loadAnimation(context, R.anim.enlarge_main_fab))
            mainFab.setOnClickListener {
                fabIsNotExpanded = if (fabIsNotExpanded) {
                    showFabs()
                    setOnDeleteFabClickListener()
                    setOnAddFabClickListener()
                    setOnNextStepFabClickListener()
                    false
                } else {
                    hideFabs()
                    true
                }
            }
        }
    }

    private fun setOnDeleteFabClickListener() {
        delete_fab.setOnClickListener {
            if (count!! > 1) {
                childFragmentManager.beginTransaction()
                    .detach(fragmentContainer[count?.minus(1)!!].findFragment()).commit()
                count = childFragmentManager.fragments.size - 1
                hideFabs()
                fabIsNotExpanded = true
            }
        }
    }

    private fun setOnAddFabClickListener() {
        add_fab.setOnClickListener {
            val fragment = QuestionsFragment()
            count = childFragmentManager.fragments.size + 1
            childFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, "FragQuestion $count").commit()
            hideFabs()
            fabIsNotExpanded = true
        }
    }

    private fun setOnNextStepFabClickListener() {
        next_step_fab.setOnClickListener {
            var emptyField = false
            for (i in 1..count!!) {
                val frag =
                    childFragmentManager.findFragmentByTag("FragQuestion $i") as QuestionsFragment
                val emptyQuestionField = frag.view?.textEditTextQuestion?.text.toString() == ""
                val emptyTwoAnswersField =
                    (frag.view?.textEditTextAnswerOne?.text.toString() == "" && frag.view?.textEditTextAnswerTwo?.text.toString() == "") ||
                            (frag.view?.textEditTextAnswerTwo?.text.toString() == "" && frag.view?.textEditTextAnswerThree?.text.toString() == "") ||
                            (frag.view?.textEditTextAnswerOne?.text.toString() == "" && frag.view?.textEditTextAnswerThree?.text.toString() == "") ||
                            (frag.view?.textEditTextAnswerOne?.text.toString() == "" && frag.view?.textEditTextAnswerTwo?.text.toString() == "" && frag.view?.textEditTextAnswerThree?.text.toString() == "")

                if (!emptyQuestionField && !emptyTwoAnswersField) {
                    createQuestionsViewModel.setQuestions(frag)
                } else if (emptyQuestionField) {
                    frag.view?.textEditTextQuestion?.error = getString(R.string.input_question)

                    val snackBar = Snackbar.make(
                        it,
                        resources.getString(R.string.empty_question),
                        Snackbar.LENGTH_SHORT
                    ).setAction(getString(R.string.ok)) { }
                    snackBar.anchorView = main_fab
                    snackBar.show()
                    emptyField = true
                    createQuestionsViewModel.clearQuestionsList()
                } else if (emptyTwoAnswersField) {
                    val snackBar = Snackbar.make(
                        it,
                        resources.getString(R.string.input_question_text_snackbar),
                        Snackbar.LENGTH_LONG
                    )
                    snackBar.anchorView = main_fab
                    snackBar.show()
                    emptyField = true
                    createQuestionsViewModel.clearQuestionsList()
                }
            }
            if (!emptyField) {
                createQuestionsViewModel.setMaxScore()
                createQuestionsViewModel.getTestLiveData().observe(viewLifecycleOwner) { test ->
                    navigateToNextStepResult(test)
                }
            }
            hideFabs()
            fabIsNotExpanded = true
        }
    }

    private fun hideFabs() {
        val layoutParamsDeleteFab = delete_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsDeleteFab.rightMargin -= (delete_fab.width * 3.5).toInt()
        layoutParamsDeleteFab.bottomMargin -= (delete_fab.height * 0.0).toInt()
        delete_fab.layoutParams = layoutParamsDeleteFab
        delete_fab.startAnimation(loadAnimation(context, R.anim.delete_fab_hide))
        delete_fab.isClickable = false

        val layoutParamsAddFab = add_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsAddFab.rightMargin -= (add_fab.width * 2.5).toInt()
        layoutParamsAddFab.bottomMargin -= (add_fab.height * 0.0).toInt()
        add_fab.layoutParams = layoutParamsAddFab
        add_fab.startAnimation(loadAnimation(context, R.anim.add_fab_hide))
        add_fab.isClickable = false

        val layoutParamsNextStepFab = next_step_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsNextStepFab.rightMargin -= (next_step_fab.width * 1.5).toInt()
        layoutParamsNextStepFab.bottomMargin -= (next_step_fab.height * 0.0).toInt()
        next_step_fab.layoutParams = layoutParamsNextStepFab
        next_step_fab.startAnimation(loadAnimation(context, R.anim.next_step_fab_hide))
        next_step_fab.isClickable = false
    }

    private fun showFabs() {
        val layoutParamsDeleteFab = delete_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsDeleteFab.rightMargin += (delete_fab.width * 3.5).toInt()
        layoutParamsDeleteFab.bottomMargin += (delete_fab.height * 0.0).toInt()
        delete_fab.layoutParams = layoutParamsDeleteFab
        delete_fab.startAnimation(loadAnimation(context, R.anim.delete_fab_show))
        delete_fab.isClickable = true

        val layoutParamsAddFab = add_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsAddFab.rightMargin += (add_fab.width * 2.5).toInt()
        layoutParamsAddFab.bottomMargin += (add_fab.height * 0.0).toInt()
        add_fab.layoutParams = layoutParamsAddFab
        add_fab.startAnimation(loadAnimation(context, R.anim.add_fab_show))
        add_fab.isClickable = true

        val layoutParamsNextStepFab = next_step_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsNextStepFab.rightMargin += (next_step_fab.width * 1.5).toInt()
        layoutParamsNextStepFab.bottomMargin += (next_step_fab.height * 0.0).toInt()
        next_step_fab.layoutParams = layoutParamsNextStepFab
        next_step_fab.startAnimation(loadAnimation(context, R.anim.next_step_fab_show))
        next_step_fab.isClickable = true
    }

    private fun navigateToNextStepResult(test: TempTest) {
        (requireActivity() as MainActivity).navigateToNextStepResult(test)
    }

}