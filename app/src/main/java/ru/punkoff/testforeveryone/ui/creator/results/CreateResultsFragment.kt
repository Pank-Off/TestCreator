package ru.punkoff.testforeveryone.ui.creator.results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fragment_create_questions.*
import kotlinx.android.synthetic.main.item_fragment_results.*
import kotlinx.android.synthetic.main.item_fragment_results.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.databinding.FragmentCreateResultsBinding

class CreateResultsFragment : Fragment() {
    private val test: TempTest? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            TempTest.EXTRA_TEMP_TEST
        )
    }
    private val createResultsViewModel by viewModel<CreateResultsViewModel> {
        parametersOf(test)
    }
    private var fabIsNotExpanded = true
    private var count: Int? = 0
    private var _binding: FragmentCreateResultsBinding? = null
    private val binding: FragmentCreateResultsBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (count == 0) {
            addResultFragment()
        }
        with(binding) {
            next_step_fab.setImageResource(android.R.drawable.ic_menu_save)

            mainFab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.enlarge_main_fab))
            mainFab.setOnClickListener {
                fabIsNotExpanded = if (fabIsNotExpanded) {
                    showFabs()
                    setOnDeleteFabClickListener()
                    setOnAddFabClickListener()
                    setOnSaveFabClickListener()
                    false
                } else {
                    hideFabs()
                    true
                }
            }

        }
    }

    private fun addResultFragment() {
        val fragment = ResultsFragment()
        count = childFragmentManager.fragments.size + 1
        Log.d(javaClass.simpleName, "count $count")
        childFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment, "FragResult $count").commit()
    }

    private fun deleteResultFragment() {
        childFragmentManager.beginTransaction()
            .detach(fragmentContainer[count?.minus(1)!!].findFragment()).commit()
        count = childFragmentManager.fragments.size - 1
    }

    private fun setOnSaveFabClickListener() {
        next_step_fab.setOnClickListener {
            var correctMaxScore = true
            val correctInput = checkTextFieldCorrectInput()
            for (i in 1..count!!) {
                val frag =
                    childFragmentManager.findFragmentByTag("FragResult $i") as ResultsFragment

                val textFromEditTextTo = frag.view?.textEditTextTo?.text.toString()
                val score =
                    if (textFromEditTextTo == "") 0 else textFromEditTextTo.toInt()
                val maxScore = test?.getMaxScore()
                if (score > maxScore!!) {
                    Snackbar.make(
                        it,
                        "${getString(R.string.your_score_is_limited_maxScore)} (${maxScore})",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    correctMaxScore = false
                } else {
                    createResultsViewModel.setResults(frag)
                }
            }
            if (correctMaxScore && correctInput) {
                createResultsViewModel.saveTest()
                GlobalScope.launch(Dispatchers.Main) {
                    delay(100)
                    Toast.makeText(context, getString(R.string.save), Toast.LENGTH_SHORT).show()
                    navigateToYourTests()
                }
            } else if (!correctInput) {
                if (textInputTitle.text.toString() == "") {
                    textInputTitle.error = getString(R.string.input_title)
                }
                if (textInputDescription.text.toString() == "") {
                    textInputDescription.error = getString(R.string.input_description)
                }
            }
        }
    }

    private fun checkTextFieldCorrectInput(): Boolean {
        try {
            if (textInputTitle.text.toString() != "" && textInputDescription.text.toString() != ""
                && textInputTitle.text?.length!! <= textFieldTitle.counterMaxLength && textInputDescription.text?.length!! <= textFieldDescription.counterMaxLength
            ) {
                return true
            }
        } catch (exc: NullPointerException) {
            return true
        }
        return false
    }

    private fun setOnDeleteFabClickListener() {
        delete_fab.setOnClickListener {
            if (count!! > 1) {
                deleteResultFragment()
                hideFabs()
                fabIsNotExpanded = true
            }
        }
    }

    private fun setOnAddFabClickListener() {
        add_fab.setOnClickListener {
            addResultFragment()
            hideFabs()
            fabIsNotExpanded = true
        }
    }

    private fun showFabs() {
        val layoutParamsDeleteFab = delete_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsDeleteFab.rightMargin += (delete_fab.width * 3.5).toInt()
        layoutParamsDeleteFab.bottomMargin += (delete_fab.height * 0.0).toInt()
        delete_fab.layoutParams = layoutParamsDeleteFab
        delete_fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.delete_fab_show))
        delete_fab.isClickable = true

        val layoutParamsAddFab = add_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsAddFab.rightMargin += (add_fab.width * 2.5).toInt()
        layoutParamsAddFab.bottomMargin += (add_fab.height * 0.0).toInt()
        add_fab.layoutParams = layoutParamsAddFab
        add_fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.add_fab_show))
        add_fab.isClickable = true

        val layoutParamsNextStepFab = next_step_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsNextStepFab.rightMargin += (next_step_fab.width * 1.5).toInt()
        layoutParamsNextStepFab.bottomMargin += (next_step_fab.height * 0.0).toInt()
        next_step_fab.layoutParams = layoutParamsNextStepFab
        next_step_fab.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.next_step_fab_show
            )
        )
        next_step_fab.isClickable = true
    }

    private fun hideFabs() {
        val layoutParamsDeleteFab = delete_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsDeleteFab.rightMargin -= (delete_fab.width * 3.5).toInt()
        layoutParamsDeleteFab.bottomMargin -= (delete_fab.height * 0.0).toInt()
        delete_fab.layoutParams = layoutParamsDeleteFab
        delete_fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.delete_fab_hide))
        delete_fab.isClickable = false

        val layoutParamsAddFab = add_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsAddFab.rightMargin -= (add_fab.width * 2.5).toInt()
        layoutParamsAddFab.bottomMargin -= (add_fab.height * 0.0).toInt()
        add_fab.layoutParams = layoutParamsAddFab
        add_fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.add_fab_hide))
        add_fab.isClickable = false

        val layoutParamsNextStepFab = next_step_fab.layoutParams as FrameLayout.LayoutParams
        layoutParamsNextStepFab.rightMargin -= (next_step_fab.width * 1.5).toInt()
        layoutParamsNextStepFab.bottomMargin -= (next_step_fab.height * 0.0).toInt()
        next_step_fab.layoutParams = layoutParamsNextStepFab
        next_step_fab.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.next_step_fab_hide
            )
        )
        next_step_fab.isClickable = false
    }

    private fun navigateToYourTests() {
        (requireActivity() as MainActivity).navigateToYourTests()
    }
}