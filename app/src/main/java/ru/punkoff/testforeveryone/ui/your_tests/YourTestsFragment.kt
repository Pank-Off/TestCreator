package ru.punkoff.testforeveryone.ui.your_tests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.databinding.FragmentYourTestsBinding
import ru.punkoff.testforeveryone.ui.adapter.TestsAdapter
import ru.punkoff.testforeveryone.ui.all_tests.TestsViewState
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestFragment

class YourTestsFragment : Fragment() {

    private val yourTestsViewModel by viewModel<YourTestsViewModel>()
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
        adapter.attachListener {
            Log.d(javaClass.simpleName, "Color: ${it.color}")
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
                        yourTestsViewModel.deleteTest(it.testId)
                        adapter.notifyDataSetChanged()
                    }.show()
            }
        }
        with(binding) {
            listTests.adapter = adapter
            listTests.layoutManager = LinearLayoutManager(context)
            fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.enlarge_main_fab))
            fab.setOnClickListener {
                navigateTo(null)
                fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shrink_main_fab))
            }
        }

        yourTestsViewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is TestsViewState.Value -> {
                    Log.d(javaClass.simpleName, "observe: $it")
                    adapter.submitList(it.tests)
                    Log.d(javaClass.simpleName, it.tests.toString())
                }
                TestsViewState.EMPTY -> Unit
            }
        }
    }

    private fun navigateTo(test: TestEntity?) {
        (requireActivity() as MainActivity).navigateTo(TestFragment.create(test))
    }

}