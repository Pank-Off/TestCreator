package ru.punkoff.testforeveryone.ui.activities

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.errors.NoAuthException

abstract class BaseActivity : AppCompatActivity() {

    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startForResult.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_title_icon)
                .setTheme(R.style.Theme_TestForEveryone)
                .setAvailableProviders(providers)
                .build()
        )
    }

    protected abstract val startForResult: ActivityResultLauncher<Intent>

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    protected fun renderData() {
        startMainActivity()
    }

    protected open fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> startLoginActivity()
            else -> error.message?.let { showError(it) }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}