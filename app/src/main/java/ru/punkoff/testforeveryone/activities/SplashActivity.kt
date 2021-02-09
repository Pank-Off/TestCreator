package ru.punkoff.testforeveryone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.errors.NoAuthException


class SplashActivity : BaseActivity() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val currentUser
        get() = mAuth.currentUser
    private val splashViewModel by viewModel<SplashViewModel> {
        parametersOf(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        findViewById<Button>(R.id.sign_in_btn).setOnClickListener {
            splashViewModel.observeViewState().observe(this) {
                when (it) {
                    is SplashViewState.Error -> renderError(NoAuthException())
                    SplashViewState.Auth -> renderData(currentUser)
                }
            }
        }

        findViewById<MaterialButton>(R.id.anonymous_btn).setOnClickListener {
            mAuth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(javaClass.simpleName, "signInAnonymously:success")
                        renderData(currentUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(javaClass.simpleName, "signInAnonymously:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        renderData(null)
                    }

                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode != RC_SIGN_IN -> return
            resultCode != RESULT_OK -> Toast.makeText(
                this,
                "Internet unavailable",
                Toast.LENGTH_SHORT
            ).show()
            resultCode == RESULT_OK -> renderData(currentUser)
        }
    }
}