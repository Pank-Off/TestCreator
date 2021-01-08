package ru.punkoff.testforeveryone.ui.creator.results

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.databinding.FragmentCreateResultsBinding

class CreateResultsFragment : Fragment() {
    private lateinit var createResultsViewModel: CreateResultsViewModel

    private var count: Int? = 0
    private var _binding: FragmentCreateResultsBinding? = null
    private val binding: FragmentCreateResultsBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(javaClass.simpleName, "Pochemu ${Repository.test}")
        createResultsViewModel =
            ViewModelProvider(this).get(CreateResultsViewModel::class.java)

        with(binding) {
            addResultsBtn.setOnClickListener {
                val fragment = ResultsFragment()
                count = childFragmentManager.fragments.size + 1
                Log.d(javaClass.simpleName, "count $count")
                childFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, "FragResult $count").commit()
            }
            saveBtn.setOnClickListener {
                for (i in 1..count!!) {
                    val frag =
                        childFragmentManager.findFragmentByTag("FragResult $i") as ResultsFragment
                    createResultsViewModel.setResults(frag)
                }

                createResultsViewModel.saveTest()
                Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
                navigateToYourTests()
            }
        }
    }

    private fun navigateToYourTests() {
        (requireActivity() as MainActivity).navigateToYourTests()
    }
}