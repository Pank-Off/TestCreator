package ru.punkoff.testforeveryone.ui.creator.results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
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
import java.lang.NullPointerException

class CreateResultsFragment : Fragment() {
    private val test: TempTest? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            TempTest.EXTRA_TEMP_TEST
        )
    }
    private val createResultsViewModel by viewModel<CreateResultsViewModel> {
        parametersOf(test)
    }

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

        with(binding) {
            addResultsBtn.setOnClickListener {
                val fragment = ResultsFragment()
                count = childFragmentManager.fragments.size + 1
                Log.d(javaClass.simpleName, "count $count")
                childFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, "FragResult $count").commit()
            }
            saveBtn.setOnClickListener {
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
                            "Your score is limited maxScore (${maxScore})",
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
                        Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
                        navigateToYourTests()
                    }
                } else if (!correctInput) {
                    if (textInputTitle.text.toString() == "") {
                        textInputTitle.error = "Input Title"
                    }
                    if (textInputDescription.text.toString() == "") {
                        textInputDescription.error = "Input Description"
                    }
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

    private fun navigateToYourTests() {
        (requireActivity() as MainActivity).navigateToYourTests()
    }
}