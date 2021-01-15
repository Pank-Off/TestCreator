package ru.punkoff.testforeveryone.ui.share_about_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.punkoff.testforeveryone.ui.your_tests.play_test.result.ShowResultViewModel

class ShareAboutUsFragment : Fragment() {

    private val shareAboutUsViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ShareAboutUsViewModel() as T
            }
        }).get(ShareAboutUsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().popBackStack()
        val intent = shareAboutUsViewModel.setOnShareBtnClickListener()
        startActivity(Intent.createChooser(intent, "Share using"))
    }
}