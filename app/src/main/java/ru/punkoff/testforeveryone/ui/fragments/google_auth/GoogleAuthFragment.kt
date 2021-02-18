package ru.punkoff.testforeveryone.ui.fragments.google_auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.ui.activities.MainActivity

class GoogleAuthFragment : Fragment() {

    private val googleLogOutViewModel by viewModel<GoogleAuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().popBackStack()
        (requireActivity() as MainActivity).showLogoutDialog()
    }
}