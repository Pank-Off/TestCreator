package ru.punkoff.testforeveryone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.R
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
                SplashViewState.Auth -> renderData()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(javaClass.simpleName, "ResultCode: $resultCode")
        when {
            requestCode != RC_SIGN_IN -> return
            resultCode != RESULT_OK -> showLogInOfflineDialog()
            resultCode == RESULT_OK -> renderData()
        }
    }

    private fun showLogInOfflineDialog() {
        MaterialAlertDialogBuilder(this@SplashActivity)
            .setTitle(resources.getString(R.string.logInOfflineTitleText))
            .setMessage(resources.getString(R.string.logInOfflineMessage))
            .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                renderData()
            }.show()
    }
}
