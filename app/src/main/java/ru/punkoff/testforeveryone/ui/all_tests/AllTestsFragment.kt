package ru.punkoff.testforeveryone.ui.all_tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.R

class AllTestsFragment : Fragment() {

    private lateinit var allTestsViewModel: AllTestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allTestsViewModel =
            ViewModelProvider(this).get(AllTestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all_tests, container, false)
        val textView: TextView = root.findViewById(R.id.text_all_tests)
        allTestsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}