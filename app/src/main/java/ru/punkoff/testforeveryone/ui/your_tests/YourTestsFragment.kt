package ru.punkoff.testforeveryone.ui.your_tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ru.punkoff.testforeveryone.MainActivity
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.model.Test
import ru.punkoff.testforeveryone.ui.creator.CreatorFragment

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
        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener {
            navigateTo(null)
        }

        return root
    }

    private fun navigateTo(test: Test?) {
        (requireActivity() as MainActivity).navigateTo(test)
    }
}