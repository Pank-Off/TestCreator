package ru.punkoff.testforeveryone.ui.fragments.rate_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel


class RateUsFragment : Fragment() {

    private val rateUsViewModel by viewModel<RateUsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().popBackStack()
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        startActivity(browserIntent)
    }
}