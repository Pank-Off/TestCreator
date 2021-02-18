package ru.punkoff.testforeveryone.ui.fragments.share_about_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.R

class ShareAboutUsFragment : Fragment() {

    private val shareAboutUsViewModel by viewModel<ShareAboutUsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().popBackStack()
        val intent = shareAboutUsViewModel.setOnShareBtnClickListener(
            shareAboutUsText = resources.getString(
                R.string.shareAboutUsText
            ), refText = resources.getString(R.string.href_on_App)
        )
        startActivity(Intent.createChooser(intent, "Share using"))
    }
}