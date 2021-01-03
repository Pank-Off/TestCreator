package ru.punkoff.testforeveryone.ui.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.databinding.FragmentCreateResultsBinding

class CreateResultsFragment : Fragment() {
    private lateinit var createResultsViewModel: CreateResultsViewModel

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
        createResultsViewModel =
            ViewModelProvider(this).get(CreateResultsViewModel::class.java)

        with(binding) {
            saveBtn.setOnClickListener {
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