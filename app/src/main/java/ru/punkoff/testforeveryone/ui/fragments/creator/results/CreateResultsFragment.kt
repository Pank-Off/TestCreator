package ru.punkoff.testforeveryone.ui.fragments.creator.results

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils.loadAnimation
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fragment_create_questions.*
import kotlinx.android.synthetic.main.item_fragment_results.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.databinding.FragmentCreateResultsBinding
import ru.punkoff.testforeveryone.ui.activities.MainActivity

class CreateResultsFragment : Fragment() {
    private val test: TempTest? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            TempTest.EXTRA_TEMP_TEST
        )
    }
    private val createResultsViewModel by viewModel<CreateResultsViewModel> {
        parametersOf(test)
    }
    private var fabIsNotExpanded = true
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
        setHasOptionsMenu(true)
        if (count == 0) {
            addResultFragmentToContainer()
        }
        with(binding) {
            next_step_fab.setImageResource(android.R.drawable.ic_menu_save)

            mainFab.startAnimation(loadAnimation(context, R.anim.enlarge_main_fab))
            mainFab.setOnClickListener {
                fabIsNotExpanded = if (fabIsNotExpanded) {
                    mainFab.showFabs(delete_fab, add_fab, next_step_fab)
                    setOnDeleteFabClickListener()
                    setOnAddFabClickListener()
                    setOnSaveFabClickListener()
                    false
                } else {
                    mainFab.hideFabs(delete_fab, add_fab, next_step_fab)
                    true
                }
            }
        }
    }

    private fun setOnSaveFabClickListener() {
        next_step_fab.setOnClickListener {
            showCloudOrSaveLocalAlert()
        }
    }


    private fun setOnDeleteFabClickListener() {
        delete_fab.setOnClickListener {
            if (count!! > 1) {
                detachResultFragmentFromContainer()
                main_fab.hideFabs(delete_fab, add_fab, next_step_fab)
                fabIsNotExpanded = true
            } else if (count == 1) {
                showAlert()
            }
        }
    }

    private fun setOnAddFabClickListener() {
        add_fab.setOnClickListener {
            addResultFragmentToContainer()
            main_fab.hideFabs(delete_fab, add_fab, next_step_fab)
            fabIsNotExpanded = true
        }
    }

    private fun addResultFragmentToContainer() {
        val fragment = ResultsFragment()
        count = childFragmentManager.fragments.size + 1
        Log.d(javaClass.simpleName, "count $count")
        childFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment, "FragResult $count").commit()
    }

    private fun detachResultFragmentFromContainer() {
        childFragmentManager.beginTransaction()
            .detach(fragmentContainer[count?.minus(1)!!].findFragment()).commit()
        count = childFragmentManager.fragments.size - 1
    }

    private fun showSnackBar(snackBarText: String) {
        val snackBar = Snackbar.make(main_fab, snackBarText, Snackbar.LENGTH_LONG)
        snackBar.show()
        snackBar.anchorView = main_fab
        snackBar.show()
        createResultsViewModel.clearResultsList()
    }

    private fun showAlert() {
        context?.let { it ->
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.delete_form_result))
                .setMessage(resources.getString(R.string.sure_without_results))
                .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                    // Respond to negative button press
                }
                .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                    detachResultFragmentFromContainer()
                    main_fab.hideFabs(delete_fab, add_fab, next_step_fab)
                    fabIsNotExpanded = true
                }.show()
        }
    }

    private fun showCloudOrSaveLocalAlert() {
        context?.let { it ->
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.cloud_save_title))
                .setMessage(resources.getString(R.string.cloud_save_message))
                .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                    saveTest(remoteSave = false)
                }
                .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                    saveTest(remoteSave = true)
                }.show()
        }
    }


    private fun saveTest(remoteSave: Boolean) {
        if (checkValidInput()) {
            createResultsViewModel.saveTest(resources.getString(R.string.create_test))
            if (remoteSave) {
                createResultsViewModel.pushTest()
            }
            GlobalScope.launch(Dispatchers.Main) {
                delay(100)
                navigateToYourTests()
            }
        }
    }

    private fun checkValidInput(): Boolean {
        var validInput = true
        for (i in 1..count!!) {
            val frag =
                childFragmentManager.findFragmentByTag("FragResult $i") as ResultsFragment

            val textEditTextTo = frag.view?.textEditTextTo?.text.toString()
            val textEditTextFrom = frag.view?.textEditTextFrom?.text.toString()
            val emptyTitleInput = frag.view?.textInputTitle?.text.toString() == ""
            val emptyDescriptionInput = frag.view?.textInputDescription?.text.toString() == ""

            val score =
                if (textEditTextTo == "") 0 else textEditTextTo.toInt()
            val maxScore = test?.getMaxScore()

            val scoreFrom = if (textEditTextFrom == "") 0 else textEditTextFrom.toInt()
            val scoreTo = if (textEditTextTo == "") 0 else textEditTextTo.toInt()
            when {
                (emptyTitleInput || emptyDescriptionInput) -> {
                    validInput = false
                    var snackBarText = getString(R.string.empty_title)
                    if (emptyDescriptionInput) {
                        snackBarText = getString(R.string.empty_description)
                        frag.view?.textInputDescription?.error =
                            getString(R.string.input_description)
                    }
                    if (emptyTitleInput) {
                        snackBarText = getString(R.string.empty_title)
                        frag.view?.textInputTitle?.error = getString(R.string.input_title)
                    }
                    showSnackBar(snackBarText)
                }
                (score > maxScore!!) -> {
                    validInput = false
                    val snackBarText =
                        "${getString(R.string.your_score_is_limited_maxScore)} (${maxScore})"
                    showSnackBar(snackBarText)
                }
                (scoreTo - scoreFrom < 0) -> {
                    validInput = false
                    val snackBarText = getString(R.string.min_score_more_then_max)
                    showSnackBar(snackBarText)

                }
                (frag.requireView().textInputTitle.text?.length!! > frag.requireView().textFieldTitle.counterMaxLength) -> {
                    validInput = false
                    createResultsViewModel.clearResultsList()
                }
                (frag.requireView().textInputDescription.text?.length!! > frag.requireView().textFieldDescription.counterMaxLength) -> {
                    validInput = false
                    createResultsViewModel.clearResultsList()
                }
                (textEditTextFrom.isEmpty() || textEditTextTo.isEmpty()) -> {
                    validInput = false
                    showSnackBar(getString(R.string.input_score))
                }
                else -> {
                    createResultsViewModel.setResults(frag)
                }
            }
        }
        return validInput
    }

    private fun navigateToYourTests() {
        (requireActivity() as MainActivity).navigateToYourTests()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.search).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.help -> context?.let {
                MaterialAlertDialogBuilder(it).setView(R.layout.help_dialog_fragment_layout).show()
            }
            android.R.id.home -> findNavController().popBackStack()
        }

        return true
    }
}