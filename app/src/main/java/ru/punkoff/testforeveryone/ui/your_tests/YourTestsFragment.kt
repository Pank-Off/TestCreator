package ru.punkoff.testforeveryone.ui.your_tests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.databinding.FragmentYourTestsBinding
import ru.punkoff.testforeveryone.ui.adapter.TestsAdapter
import ru.punkoff.testforeveryone.ui.all_tests.TestsViewState
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestFragment

class YourTestsFragment : Fragment() {

    private val yourTestsViewModel by viewModel<YourTestsViewModel>()
    private val adapter = TestsAdapter()

    private var _binding: FragmentYourTestsBinding? = null
    private val binding: FragmentYourTestsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYourTestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.attachListener {
            Log.d(javaClass.simpleName, "Color: ${it.color}")
            navigateTo(it)
        }

        with(binding) {
            listTests.adapter = adapter
            listTests.layoutManager = LinearLayoutManager(context)
            fab.setOnClickListener {

                navigateTo(null)
            }
        }

        yourTestsViewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is TestsViewState.Value -> {
                    Log.d(javaClass.simpleName, "observe: $it")
                    adapter.submitList(it.tests)
                    Log.d(javaClass.simpleName, it.tests.toString())
                }
                TestsViewState.EMPTY -> Unit
            }
        }
    }

    private fun navigateTo(test: TestEntity?) {
        (requireActivity() as MainActivity).navigateTo(TestFragment.create(test))
    }
}