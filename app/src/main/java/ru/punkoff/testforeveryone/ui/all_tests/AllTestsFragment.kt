package ru.punkoff.testforeveryone.ui.all_tests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import ru.punkoff.testforeveryone.databinding.FragmentAllTestsBinding
import ru.punkoff.testforeveryone.ui.adapter.TestsAdapter

class AllTestsFragment : Fragment() {

    private lateinit var allTestsViewModel: AllTestsViewModel

    private val adapter = TestsAdapter()

    private var _binding: FragmentAllTestsBinding? = null
    private val binding: FragmentAllTestsBinding get() = _binding!!
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
        allTestsViewModel =
            ViewModelProvider(this).get(AllTestsViewModel::class.java)
        adapter.attachListener { test ->
            Toast.makeText(
                context,
                test.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        with(binding) {
            listTests.adapter = adapter
            listTests.layoutManager = LinearLayoutManager(context)
        }

        allTestsViewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is TestsViewState.Value -> {
                    adapter.submitList(it.tests)
                    Log.d(javaClass.simpleName, it.tests.toString())
                }
                TestsViewState.EMPTY -> Unit
            }
        }
    }
}