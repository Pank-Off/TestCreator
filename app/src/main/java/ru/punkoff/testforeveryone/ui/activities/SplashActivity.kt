package ru.punkoff.testforeveryone.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.errors.NoAuthException
import ru.punkoff.testforeveryone.utils.isNetworkAvailable
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
                if (isNetworkAvailable(applicationContext)) {
                    splashViewModel.requestUser()
                } else {
                    showLogInOfflineDialog()
                }
            }

            anonymousBtn.setOnClickListener {
                if (isNetworkAvailable(applicationContext)) {
                    splashViewModel.signInAnonymously()
                } else {
                    showLogInOfflineDialog()
                }
            }
        }

        splashViewModel.observeViewState().observe(this@SplashActivity) {
            when (it) {
                is SplashViewState.Error -> renderError(NoAuthException())
                is SplashViewState.Auth -> renderData()
            }
        }
    }

    override val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK -> renderData()
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
