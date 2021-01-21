package ru.punkoff.testforeveryone.ui.your_results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.databinding.FragmentResultsBinding
import ru.punkoff.testforeveryone.ui.your_results.adapter.ResultsAdapter
import ru.punkoff.testforeveryone.ui.your_tests.play_test.result.ShowResultFragment

class YourResultsFragment : Fragment() {

    private val yourResultsViewModel by viewModel<YourResultsViewModel>()

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
        adapter.attachListener {
            navigateTo(it)
        }
        adapter.attachDeleteListener {
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(resources.getString(R.string.title_dialog))
                    .setMessage(resources.getString(R.string.supporting_text_dialog))
                    .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                        // Respond to negative button press
                    }
                    .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                        yourResultsViewModel.deleteResult(it.resultId)
                        adapter.notifyDataSetChanged()
                    }.show()
            }
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
            ShowResultFragment.create(result), null
        )
    }
}