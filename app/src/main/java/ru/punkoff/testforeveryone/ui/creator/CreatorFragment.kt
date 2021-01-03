package ru.punkoff.testforeveryone.ui.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.databinding.FragmentCreatorBinding

class CreatorFragment : Fragment() {

    private lateinit var creatorViewModel: CreatorViewModel

    private var _binding: FragmentCreatorBinding? = null
    private val binding: FragmentCreatorBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        creatorViewModel =
            ViewModelProvider(this).get(CreatorViewModel::class.java)

        with(binding) {
            nextBtn.setOnClickListener {
                //  navigateToNextStep()
                creatorViewModel.saveTest(
                    textInputTitle.text.toString(),
                    textInputDescription.text.toString()
                )

                Toast.makeText(context, "Success Save", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToNextStep() {
        (requireActivity() as MainActivity).navigateToNextStep()
    }

}