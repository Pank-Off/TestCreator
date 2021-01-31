package ru.punkoff.testforeveryone.ui.google_log_out

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.activities.MainActivity

class GoogleLogOutFragment : Fragment() {

    private val googleLogOutViewModel by viewModel<GoogleLogOutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().popBackStack()
        (requireActivity() as MainActivity).showLogoutDialog()
    }
}