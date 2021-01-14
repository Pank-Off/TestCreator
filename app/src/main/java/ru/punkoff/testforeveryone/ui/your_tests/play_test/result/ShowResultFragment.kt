package ru.punkoff.testforeveryone.ui.your_tests.play_test.result

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.data.Repository
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

    private val _scorePercent: Double =
        Repository.result.score.toDouble() / Repository.result.maxScore.toDouble() * 100
    private val scorePercent = _scorePercent

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
            scoreView.text =
                "You scored ${Repository.result.score} out of ${Repository.result.maxScore} points ($scorePercent)"
            titleView.text = Repository.result.title
            bodyView.text = Repository.result.body

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

    private fun navigateToYourResults() {
        (requireActivity() as MainActivity).navigateToYourResults()
    }
}