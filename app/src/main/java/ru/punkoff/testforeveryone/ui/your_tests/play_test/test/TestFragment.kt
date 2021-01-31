package ru.punkoff.testforeveryone.ui.your_tests.play_test.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.activities.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.data.local.room.mapToColor
import ru.punkoff.testforeveryone.databinding.FragmentTestBinding
import ru.punkoff.testforeveryone.ui.your_tests.play_test.adapter.TestAdapter

class TestFragment : Fragment() {

    private val adapter = TestAdapter()


    private var position = 0
    private var maxSize = 0
    private val test: TestEntity? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            EXTRA_TEST
        )
    }
    private val testViewModel by viewModel<TestViewModel> {
        parametersOf(test)
    }
    private var _binding: FragmentTestBinding? = null
    private val binding: FragmentTestBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test?.color?.mapToColor()?.let { view.setBackgroundResource(it) }
        testViewModel.clearScore()
        adapter.firstStart = true
        position = 0
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        activity?.let {
            ContextCompat.getDrawable(it, R.drawable.divider)?.let { drawable ->
                dividerItemDecoration.setDrawable(
                    drawable
                )
            }
        }

        Log.d(javaClass.simpleName, "Test + $test")
        with(binding) {

            adapter.attachListener { answer, score ->
                Log.d(javaClass.simpleName, "answer: $answer score $score")
                score?.toInt()?.let {
                    testViewModel.refreshScore(answer, it)
                    if (position == maxSize) {
                        showResultBtn.text = getString(R.string.show_results)
                        showResultBtn.visibility = MaterialButton.VISIBLE
                    }
                    if (position < maxSize) {
                        Log.d(javaClass.simpleName, "Position in TestFragment $position")
                        adapter.showQuestion(position++)
                    }
                }
            }

            questionList.adapter = adapter
            questionList.layoutManager = LinearLayoutManager(context)
            questionList.addItemDecoration(dividerItemDecoration)
            titleView.text = test?.title
            showResultBtn.setOnClickListener {
                showResultBtn.visibility = MaterialButton.GONE

                adapter.showQuestion(position++)
                Log.d(javaClass.simpleName, "Position: $position vs. MaxSize: $maxSize")
                if (position - 1 == maxSize) {
                    showResult()
                }
            }
        }


        testViewModel.observeViewState().observe(viewLifecycleOwner) {
            Log.d(javaClass.simpleName, "questions: ${it?.questions}")
            adapter.submitList(it?.questions)
            maxSize = it?.questions?.size!!
        }
    }

    private fun showResult() {
        var score = 0
        testViewModel.getScore().value?.forEach {
            Log.d("getScore: ", it.value.toString())
            score += it.value
        }
        test?.let { it1 -> testViewModel.createResult(it1, score) }
        Log.d(javaClass.simpleName, "Score: $score ")
        testViewModel.getResultLiveData().observe(viewLifecycleOwner) {
            Log.d(javaClass.simpleName, "getResultLiveData: ${it.getResult()}")
            navigateToShowResultFragment(it)
        }
    }

    private fun navigateToShowResultFragment(result: TempResult) {
        (requireActivity() as MainActivity).navigateToShowResultFragment(null, result)
    }

    companion object {
        const val EXTRA_TEST = "arg"
        fun create(test: TestEntity?): TestFragment {
            val testFragment = TestFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_TEST, test)
            testFragment.arguments = bundle
            Log.d(TestFragment::class.java.simpleName, "Bundle: $test")
            return testFragment
        }
    }
}