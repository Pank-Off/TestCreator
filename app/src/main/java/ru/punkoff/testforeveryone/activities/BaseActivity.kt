package ru.punkoff.testforeveryone.activities

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.errors.NoAuthException

const val RC_SIGN_IN = 458

open class BaseActivity : AppCompatActivity() {

    private fun startLoginActivity() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_menu_camera)
                .setTheme(AuthUI.getDefaultTheme())
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun startMainActivity(user: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_USER, user)
        Log.d(javaClass.simpleName, "INTENT user: $user")
        startActivity(intent)
        finish()
    }

    protected fun renderData(user: FirebaseUser?) {
        startMainActivity(user)
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

    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

}