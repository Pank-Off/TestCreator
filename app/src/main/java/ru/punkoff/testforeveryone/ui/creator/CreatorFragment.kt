package ru.punkoff.testforeveryone.ui.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.punkoff.testforeveryone.R

class CreatorFragment : Fragment() {

    private lateinit var creatorViewModel: CreatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creatorViewModel =
            ViewModelProvider(this).get(CreatorViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_creator, container, false)
        val textView: TextView = root.findViewById(R.id.text_creator)
        creatorViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}