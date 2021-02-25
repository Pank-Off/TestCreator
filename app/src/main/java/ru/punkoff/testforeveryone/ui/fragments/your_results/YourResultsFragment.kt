package ru.punkoff.testforeveryone.ui.fragments.your_results

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.ui.activities.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.databinding.FragmentResultsBinding
import ru.punkoff.testforeveryone.ui.fragments.your_results.adapter.ResultsAdapter
import ru.punkoff.testforeveryone.ui.fragments.your_tests.play_test.result.ShowResultFragment

class YourResultsFragment : Fragment() {

    private val yourResultsViewModel by viewModel<YourResultsViewModel>()

    private val adapter = ResultsAdapter()

    private var _binding: FragmentResultsBinding? = null
    private val binding: FragmentResultsBinding get() = _binding!!

    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener

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
        setHasOptionsMenu(true)
        adapter.attachListener {
            navigateTo(it)
        }
        adapter.attachDeleteListener { result ->
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(resources.getString(R.string.title_dialog))
                    .setMessage(resources.getString(R.string.supporting_text_dialog))
                    .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                        // Respond to negative button press
                    }
                    .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                        yourResultsViewModel.deleteResult(result.resultId)
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
                    Log.d(javaClass.simpleName, "observe: ${it.tests}")
                    adapter.submitList(it.tests)
                }
                ResultsViewState.EMPTY -> Unit
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.help).isVisible = false
        val searchItem: MenuItem = menu.findItem(R.id.search)
        // Associate searchable configuration with the SearchView
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("onQueryTextSubmit", query)
                adapter.filter.filter(query)
                Log.i("ItemCount()", adapter.itemCount.toString())
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.i("onQueryTextChange", newText)
                adapter.filter.filter(newText)
                Log.i("ItemCount()", adapter.itemCount.toString())
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            return false
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }


    private fun navigateTo(result: ResultEntity?) {
        (requireActivity() as MainActivity).navigateToShowResultFragment(
            ShowResultFragment.create(result), null
        )
    }
}