package ru.punkoff.testforeveryone.ui.your_results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.R

class YourResultsFragment : Fragment() {

    private lateinit var yourResultsViewModel: YourResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        yourResultsViewModel =
            ViewModelProvider(this).get(YourResultsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_results, container, false)
        val textView: TextView = root.findViewById(R.id.text_results)
        yourResultsViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}