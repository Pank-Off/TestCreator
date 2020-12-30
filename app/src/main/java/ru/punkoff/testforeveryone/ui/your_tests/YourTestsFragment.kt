package ru.punkoff.testforeveryone.ui.your_tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.R

class YourTestsFragment : Fragment() {

    private lateinit var yourTestsViewModel: YourTestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        yourTestsViewModel =
            ViewModelProvider(this).get(YourTestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_your_tests, container, false)
        val textView: TextView = root.findViewById(R.id.text_your_tests)
        yourTestsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}