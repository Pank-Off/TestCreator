package ru.punkoff.testforeveryone.ui.your_tests.play_test.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.TempResult.Companion.EXTRA_TEMP_RESULT
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.data.local.room.mapToColor
import ru.punkoff.testforeveryone.databinding.FragmentShowResultBinding
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestFragment

class ShowResultFragment : Fragment() {

    private var _binding: FragmentShowResultBinding? = null
    private val binding: FragmentShowResultBinding get() = _binding!!

    private val result: ResultEntity? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            EXTRA_RESULT
        )
    }
    private val tempResult: TempResult? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            EXTRA_TEMP_RESULT
        )
    }
    private val showResultViewModel by viewModel<ShowResultViewModel> {
        parametersOf(tempResult)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(false)
        requireActivity().actionBar?.setHomeButtonEnabled(false)
        with(binding) {
            if (result != null) {

                Log.d(javaClass.simpleName, "This is result $result")
                result?.color?.mapToColor()?.let { view.setBackgroundResource(it) }
                val scorePercent: Double =
                    result!!.score.toDouble() / result!!.maxScore.toDouble() * 100
                scoreView.text =
                    "You scored ${result?.score} out of ${result?.maxScore} points ($scorePercent)"
                titleView.text = result?.title
                bodyView.text = result?.body
                saveResultBtn.isVisible = false
                restartBtn.setOnClickListener {
                    requireActivity().findNavController(R.id.nav_host_fragment).popBackStack()
                    showResultViewModel.observeTest().observe(viewLifecycleOwner) {
                        navigateToRestartTest(it)
                    }
                }
            } else {
                Log.d(javaClass.simpleName, "RESULT MAZAFAKA: ${tempResult.toString()}")
                tempResult?.getColor()?.mapToColor()?.let { view.setBackgroundResource(it) }
                val scorePercent: Double =
                    tempResult!!.getScore().toDouble() / tempResult!!.getMaxScore().toDouble() * 100
                scoreView.text =
                    "You scored ${tempResult?.getScore()} out of ${tempResult?.getMaxScore()} points ($scorePercent)"
                titleView.text = tempResult?.getTitle()
                bodyView.text = tempResult?.getBody()
                restartBtn.setOnClickListener {
                    requireActivity().findNavController(R.id.nav_host_fragment).popBackStack()
                }
            }

            saveResultBtn.setOnClickListener {
                showResultViewModel.saveResult()
                Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
                navigateToYourResults()
            }

            shareBtn.setOnClickListener {
                val intent: Intent = showResultViewModel.setOnShareBtnClickListener()
                startActivity(Intent.createChooser(intent, "Share using"))
            }
        }
        result?.testTitle?.let { showResultViewModel.getTestByTitle(it) }
    }

    companion object {
        const val EXTRA_RESULT = "arg"
        fun create(result: ResultEntity?): ShowResultFragment {
            val resultFragment = ShowResultFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_RESULT, result)
            resultFragment.arguments = bundle
            Log.d(ShowResultFragment::class.java.simpleName, "Bundle: $result")
            return resultFragment
        }
    }

    private fun navigateToYourResults() {
        (requireActivity() as MainActivity).navigateToYourResults()
    }

    private fun navigateToRestartTest(test: TestEntity?) {
        (requireActivity() as MainActivity).navigateTo(TestFragment.create(test))
    }
}