package ru.punkoff.testforeveryone.ui.your_tests

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
import ru.punkoff.testforeveryone.databinding.FragmentYourTestsBinding
import ru.punkoff.testforeveryone.model.Test
import ru.punkoff.testforeveryone.ui.adapter.TestsAdapter
import ru.punkoff.testforeveryone.ui.all_tests.TestsViewState

class YourTestsFragment : Fragment() {


    private lateinit var yourTestsViewModel: YourTestsViewModel
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
        yourTestsViewModel =
            ViewModelProvider(this).get(YourTestsViewModel::class.java)
        adapter.attachListener {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
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
                    adapter.submitList(it.tests)
                    Log.d(javaClass.simpleName, it.tests.toString())
                }
                TestsViewState.EMPTY -> Unit
            }
        }
    }

    private fun navigateTo(test: Test?) {
        (requireActivity() as MainActivity).navigateTo(test)
    }
}