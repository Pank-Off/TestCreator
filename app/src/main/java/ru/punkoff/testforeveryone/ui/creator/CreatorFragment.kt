package ru.punkoff.testforeveryone.ui.creator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.databinding.FragmentCreatorBinding

class CreatorFragment : Fragment() {

    private val creatorViewModel by viewModel<CreatorViewModel>()

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
        with(binding) {
            if (textInputTitle.text?.length!! > textFieldTitle.counterMaxLength) {
                textFieldTitle.helperText = getString(R.string.max_length)
                Log.d(javaClass.simpleName, "helperText: ${textFieldTitle.helperText}")
            }
            if (textInputDescription.text?.length!! > textFieldDescription.counterMaxLength) {
                textFieldDescription.helperText = getString(R.string.max_length)
            }
            nextBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.enlarge_main_fab))
            nextBtn.setOnClickListener {
                if (textInputTitle.text.toString() != "" && textInputDescription.text.toString() != ""
                    && textInputTitle.text?.length!! <= textFieldTitle.counterMaxLength && textInputDescription.text?.length!! <= textFieldDescription.counterMaxLength
                ) {
                    creatorViewModel.createTest(
                        textInputTitle.text.toString(),
                        textInputDescription.text.toString()
                    )
                    creatorViewModel.getTest().observe(viewLifecycleOwner) {
                        navigateToNextStep(it)
                        nextBtn.startAnimation(
                            AnimationUtils.loadAnimation(
                                context,
                                R.anim.shrink_main_fab
                            )
                        )
                    }
                } else {
                    Log.d(javaClass.simpleName, "Length: ${textInputTitle.text?.length}")
                    Log.d(javaClass.simpleName, "MaxLength ${textFieldTitle.counterMaxLength}")
                    if (textInputTitle.text.toString() == "") {
                        textInputTitle.error = getString(R.string.input_title)
                    }
                    if (textInputDescription.text.toString() == "") {
                        textInputDescription.error = getString(R.string.input_description)
                    }
                }
            }
        }
    }

    private fun navigateToNextStep(test: TempTest) {
        (requireActivity() as MainActivity).navigateToNextStep(test)
    }
}