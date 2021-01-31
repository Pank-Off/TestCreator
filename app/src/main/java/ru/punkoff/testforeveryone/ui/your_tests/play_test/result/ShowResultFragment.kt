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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.activities.MainActivity
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.TempResult.Companion.EXTRA_TEMP_RESULT
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.data.local.room.mapToColor
import ru.punkoff.testforeveryone.databinding.FragmentShowResultBinding
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestFragment
import kotlin.math.floor

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
                val scoreViewText = String.format(
                    resources.getString(R.string.you_scored) + " " + result?.score + " " + resources.getString(
                        R.string.out_of
                    ) + " " + result?.maxScore + " " + resources.getString(R.string.points) + " " + "(${
                        floor(scorePercent * 100) / 100
                    })"
                )
                scoreView.text = scoreViewText

                titleView.text = result?.title
                bodyView.text = result?.body
                saveResultBtn.isVisible = false
                restartBtn.setOnClickListener {
                    requireActivity().findNavController(R.id.nav_host_fragment).popBackStack()
                    showResultViewModel.observeTest().observe(viewLifecycleOwner) {
                        navigateToRestartTest(it)
                    }
                }

                shareBtn.setOnClickListener {
                    val shareText = String.format(
                        getString(R.string.i_scored) + " " + result?.score + " " + getString(R.string.points) + " " + getString(
                            R.string.out_of
                        ) + " " + result?.maxScore + " " + getString(R.string.in_string) + " " + getString(
                            R.string.the
                        ) + " " + """"${result?.testTitle}" """ + " " + getString(R.string.test) + " ." +
                                getString(R.string.how_much_will_you_gain) + "\n" + getString(R.string.href_on_App)
                    )


                    val intent: Intent = showResultViewModel.setOnShareBtnClickListener(shareText)
                    startActivity(Intent.createChooser(intent, "Share using"))
                }
            } else {
                Log.d(javaClass.simpleName, "RESULT MAZAFAKA: ${tempResult.toString()}")
                tempResult?.getColor()?.mapToColor()?.let { view.setBackgroundResource(it) }
                val scorePercent: Double =
                    tempResult!!.getScore().toDouble() / tempResult!!.getMaxScore().toDouble() * 100

                val scoreViewText = String.format(
                    resources.getString(R.string.you_scored) + " " + tempResult?.getScore() + " " + resources.getString(
                        R.string.out_of
                    ) + " " + tempResult?.getMaxScore() + " " + resources.getString(R.string.points) + " " + "(${
                        floor(
                            scorePercent * 100
                        ) / 100
                    })"
                )
                scoreView.text = scoreViewText
                titleView.text = tempResult?.getTitle()
                bodyView.text = tempResult?.getBody()
                restartBtn.setOnClickListener {
                    requireActivity().findNavController(R.id.nav_host_fragment).popBackStack()
                }

                shareBtn.setOnClickListener {
                    val shareText = String.format(
                        getString(R.string.i_scored) + " " + tempResult?.getScore() + " " + getString(
                            R.string.points
                        ) + " " + getString(
                            R.string.out_of
                        ) + " " + tempResult?.getMaxScore() + " " + getString(R.string.in_string) + " " + getString(
                            R.string.the
                        ) + " " + """"${tempResult?.getTestTitle()}" """ + " " + getString(R.string.test) + ". " +
                                getString(R.string.how_much_will_you_gain) + "\n" + getString(R.string.href_on_App)
                    )
                    val intent: Intent = showResultViewModel.setOnShareBtnClickListener(shareText)
                    startActivity(Intent.createChooser(intent, "Share using"))
                }
            }

            saveResultBtn.setOnClickListener {
                showResultViewModel.saveResult()
                GlobalScope.launch(Dispatchers.Main) {
                    delay(100)
                    Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
                    navigateToYourResults()
                }
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