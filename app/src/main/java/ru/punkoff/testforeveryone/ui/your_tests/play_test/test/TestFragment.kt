package ru.punkoff.testforeveryone.ui.your_tests.play_test.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.data.local.room.mapToColor
import ru.punkoff.testforeveryone.databinding.FragmentTestBinding
import ru.punkoff.testforeveryone.ui.your_tests.play_test.adapter.TestAdapter

class TestFragment : Fragment() {

    private val adapter = TestAdapter()

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
        Log.d(javaClass.simpleName, "Test + $test")
        adapter.attachListener { answer, score ->
            Log.d(javaClass.simpleName, "answer: $answer score $score")
            score?.toInt()?.let { testViewModel.refreshScore(answer, it) }
        }
        with(binding) {
            questionList.adapter = adapter
            questionList.layoutManager = LinearLayoutManager(context)
            titleView.text = test?.title
            showResultBtn.setOnClickListener {
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
        }

        testViewModel.observeViewState().observe(viewLifecycleOwner) {
            Log.d(javaClass.simpleName, "questions: ${it?.questions}")
            adapter.submitList(it?.questions)
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