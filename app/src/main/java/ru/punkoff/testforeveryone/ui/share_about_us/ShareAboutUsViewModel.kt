package ru.punkoff.testforeveryone.ui.share_about_us

import android.content.Intent
import androidx.lifecycle.ViewModel

class ShareAboutUsViewModel : ViewModel() {

    fun setOnShareBtnClickListener(): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val shareBody =
            "Check out the new test creation app! I already downloaded it, and you?\nref on app in Google market"
        val shareSub = "Share sub"
        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        return intent
    }
}