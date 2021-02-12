package ru.punkoff.testforeveryone.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.data.errors.NoAuthException
import ru.punkoff.testforeveryone.databinding.ActivitySplashBinding


class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val splashViewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        with(binding) {
            signInBtn.setOnClickListener {
                splashViewModel.requestUser()
            }

            anonymousBtn.setOnClickListener {
                splashViewModel.signInAnonymously()
            }
        }

        splashViewModel.observeViewState().observe(this@SplashActivity) {
            when (it) {
                is SplashViewState.Error -> renderError(NoAuthException())
                SplashViewState.Auth -> renderData(splashViewModel.getCurrentUser())
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
            resultCode == RESULT_OK -> renderData(splashViewModel.getCurrentUser())
        }
    }
}