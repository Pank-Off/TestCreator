package ru.punkoff.testforeveryone.ui.your_tests.pass_test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.databinding.FragmentTestBinding
import ru.punkoff.testforeveryone.ui.your_tests.pass_test.adapter.TestAdapter

class TestFragment : Fragment() {

    private val adapter = TestAdapter()

    private val test: TestEntity? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            EXTRA_TEST
        )
    }
    private val testViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TestViewModel(test) as T
            }
        }).get(TestViewModel::class.java)
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
                //Log.d(javaClass.simpleName,"button click")
                var score = 0
                testViewModel.getScore().value?.forEach {
                    score += it.value
                }

                testViewModel.createResult(test, score)
                Log.d(javaClass.simpleName, "Score: $score ")

            }
        }

        testViewModel.observeViewState().observe(viewLifecycleOwner) {
            Log.d(javaClass.simpleName, "questions: ${it?.questions}")
            adapter.submitList(it?.questions)
        }

    }

    private fun navigateToShowResultFragment() {

    }

    companion object {
        const val EXTRA_TEST = "arg"
        fun create(test: TestEntity?): TestFragment {
            val testFragment = TestFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_TEST, test)
            testFragment.arguments = bundle
            Log.d("TAG", "Bundle: $test")
            return testFragment
        }
    }
}