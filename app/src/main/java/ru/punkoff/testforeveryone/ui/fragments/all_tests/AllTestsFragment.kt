package ru.punkoff.testforeveryone.ui.fragments.all_tests

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_all_tests.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.ui.activities.MainActivity
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.databinding.FragmentAllTestsBinding
import ru.punkoff.testforeveryone.ui.fragments.all_tests.adapter.AllTestsAdapter
import ru.punkoff.testforeveryone.ui.fragments.your_tests.TestsViewState
import ru.punkoff.testforeveryone.ui.fragments.your_tests.play_test.test.TestFragment
import ru.punkoff.testforeveryone.utils.hideKeyboard

class AllTestsFragment : Fragment() {

    private val allTestsViewModel by viewModel<AllTestsViewModel>()

    private val adapter = AllTestsAdapter()

    private var _binding: FragmentAllTestsBinding? = null
    private val binding: FragmentAllTestsBinding get() = _binding!!

    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllTestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        adapter.attachListener {
            navigateTo(it)
        }
        with(binding) {
            listTests.adapter = adapter
            listTests.layoutManager = LinearLayoutManager(context)
            swipeRefreshLayout.setOnRefreshListener {
                allTestsViewModel.getRequest()
                swipeRefreshLayout.isRefreshing = false
            }
        }

        allTestsViewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is TestsViewState.Value -> {
                    loading_progress_bar.visibility = ProgressBar.GONE
                    adapter.submitList(it.tests)
                    Log.d(javaClass.simpleName, it.tests.toString())
                }
                TestsViewState.EMPTY -> Toast.makeText(
                    context,
                    getString(R.string.you_are_not_logged_in),
                    Toast.LENGTH_SHORT
                ).show()
                TestsViewState.Loading -> {
                    showLoadingView()
                }
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

    private fun showLoadingView() {
        loading_progress_bar.visibility = ProgressBar.VISIBLE
    }

    private fun navigateTo(test: TestEntity?) {
        (requireActivity() as MainActivity).navigateTo(TestFragment.create(test))
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(activity)
    }
}