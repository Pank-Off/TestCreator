package ru.punkoff.testforeveryone.ui.your_tests.play_test.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.data.Repository
import ru.punkoff.testforeveryone.data.local.room.ResultEntity
import ru.punkoff.testforeveryone.databinding.FragmentShowResultBinding

class ShowResultFragment : Fragment() {

    private var _binding: FragmentShowResultBinding? = null
    private val binding: FragmentShowResultBinding get() = _binding!!

    private val showResultViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ShowResultViewModel() as T
            }
        }).get(ShowResultViewModel::class.java)
    }

    private val result: ResultEntity? by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(
            EXTRA_RESULT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(false)
        requireActivity().actionBar?.setHomeButtonEnabled(false)
        with(binding) {
            if (result != null) {
                val scorePercent: Double =
                    result!!.score.toDouble() / result!!.maxScore.toDouble() * 100
                scoreView.text =
                    "You scored ${result?.score} out of ${result?.maxScore} points ($scorePercent)"
                titleView.text = result?.title
                bodyView.text = result?.body
                saveResultBtn.isVisible = false
            } else {
                val scorePercent: Double =
                    Repository.result.score.toDouble() / Repository.result.maxScore.toDouble() * 100
                scoreView.text =
                    "You scored ${Repository.result.score} out of ${Repository.result.maxScore} points ($scorePercent)"
                titleView.text = Repository.result.title
                bodyView.text = Repository.result.body
            }
            restartBtn.setOnClickListener {
                Toast.makeText(context, "Restart test", Toast.LENGTH_SHORT).show()
            }
            saveResultBtn.setOnClickListener {
                showResultViewModel.saveResult()
                Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
                navigateToYourResults()
            }

            shareBtn.setOnClickListener {
                val intent: Intent = showResultViewModel.setOnShareBtnClickListener()
                startActivity(Intent.createChooser(intent, "Share using"))
            }
        }
    }

    companion object {
        const val EXTRA_RESULT = "arg"
        fun create(result: ResultEntity?): ShowResultFragment {
            val resultFragment = ShowResultFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_RESULT, result)
            resultFragment.arguments = bundle
            Log.d("TAG", "Bundle: $result")
            return resultFragment
        }
    }

    private fun navigateToYourResults() {
        (requireActivity() as MainActivity).navigateToYourResults()
    }
}