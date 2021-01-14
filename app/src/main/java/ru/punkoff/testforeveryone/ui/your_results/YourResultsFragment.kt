package ru.punkoff.testforeveryone.ui.your_results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.databinding.FragmentResultsBinding
import ru.punkoff.testforeveryone.ui.your_results.adapter.ResultsAdapter
import ru.punkoff.testforeveryone.ui.your_tests.play_test.result.ShowResultFragment

class YourResultsFragment : Fragment() {

    private lateinit var yourResultsViewModel: YourResultsViewModel

    private val adapter = ResultsAdapter()

    private var _binding: FragmentResultsBinding? = null
    private val binding: FragmentResultsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        yourResultsViewModel =
            ViewModelProvider(this).get(YourResultsViewModel::class.java)
        adapter.attachListener {
            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
            navigateTo(it)
        }

        with(binding) {
            listResults.adapter = adapter
            listResults.layoutManager = LinearLayoutManager(context)
        }

        yourResultsViewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is ResultsViewState.Value -> {
                    Log.d(javaClass.simpleName, "observe: $it")
                    adapter.submitList(it.tests)
                    Log.d(javaClass.simpleName, it.tests.toString())
                }
                ResultsViewState.EMPTY -> Unit
            }
        }
    }

    private fun navigateTo(result: ResultEntity?) {
        (requireActivity() as MainActivity).navigateToShowResultFragment(
            ShowResultFragment.create(
                result
            )
        )
    }
}