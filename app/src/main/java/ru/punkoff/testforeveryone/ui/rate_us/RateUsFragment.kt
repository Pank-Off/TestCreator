package ru.punkoff.testforeveryone.ui.rate_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.R

class RateUsFragment : Fragment() {

    private lateinit var rateUsViewModel: RateUsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rateUsViewModel =
            ViewModelProvider(this).get(RateUsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_rate, container, false)
        val textView: TextView = root.findViewById(R.id.text_rate)
        rateUsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}