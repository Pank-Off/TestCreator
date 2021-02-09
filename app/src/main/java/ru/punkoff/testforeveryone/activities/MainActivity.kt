package ru.punkoff.testforeveryone.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import ru.punkoff.testforeveryone.R
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.TempResult.Companion.EXTRA_TEMP_RESULT
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.data.TempTest.Companion.EXTRA_TEMP_TEST
import ru.punkoff.testforeveryone.databinding.ActivityMainBinding
import ru.punkoff.testforeveryone.ui.your_tests.play_test.result.ShowResultFragment
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestFragment
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestFragment.Companion.EXTRA_TEST

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navigationDrawerLayout = navView.getHeaderView(0)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        currentUser = intent.extras?.getParcelable(BaseActivity.EXTRA_USER)
        Log.d(
            javaClass.simpleName,
            "INTENT Extras: ${intent.extras?.getParcelable<FirebaseUser?>(BaseActivity.EXTRA_USER)}"
        )

        currentUser?.let {
            navView.menu.findItem(R.id.nav_log_out).title = getString(R.string.log_out)
            navigationDrawerLayout.findViewById<TextView>(R.id.auth_text_view).text =
                String.format(getString(R.string.welcome) + currentUser?.displayName)
            Log.d(javaClass.simpleName, "PhotoURL: ${currentUser?.photoUrl}")
            navigationDrawerLayout.findViewById<ImageView>(R.id.avatarView)
                .setImageURI(currentUser?.photoUrl)
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                //  R.id.nav_all_tests,
                R.id.nav_your_tests,
                R.id.nav_results,
                R.id.nav_creator,
                // R.id.nav_notifications,
                //  R.id.nav_settings,
                R.id.nav_share_us,
                R.id.nav_rate_us,
                R.id.nav_log_out
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun navigateTo(testFragment: TestFragment?) {
        Log.d(javaClass.simpleName, "navigateTo: ${testFragment?.arguments}")
        if (testFragment?.arguments?.get(EXTRA_TEST) == null) {
            navController.navigate(R.id.nav_creator)
        } else {
            navController.navigate(R.id.nav_pass, testFragment.arguments)
        }
    }

    fun navigateToNextStep(test: TempTest) {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_TEMP_TEST, test)
        navController.navigate(R.id.nav_create_questions, bundle)
    }

    fun navigateToNextStepResult(test: TempTest) {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_TEMP_TEST, test)
        navController.navigate(R.id.nav_create_results, bundle)
    }

    fun navigateToYourTests() {
        navController.navigate(R.id.nav_your_tests)
    }

    fun navigateToShowResultFragment(resultFragment: ShowResultFragment?, result: TempResult?) {
        if (resultFragment?.arguments?.get(EXTRA_TEST) == null) {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_TEMP_RESULT, result)
            navController.navigate(R.id.nav_show_result, bundle)
        } else {
            navController.navigate(R.id.nav_show_result, resultFragment.arguments)
        }
    }

    fun navigateToYourResults() {
        navController.navigate(R.id.nav_results)
    }

    fun showLogoutDialog() {
        var logTitleText = resources.getString(R.string.login_dialog_title)
        var logMessageText = resources.getString(R.string.login_dialog_message)
        currentUser?.let {
            logTitleText = resources.getString(R.string.logout_dialog_title)
            logMessageText = resources.getString(R.string.logout_dialog_message)
        }
        MaterialAlertDialogBuilder(this)
            .setTitle(logTitleText)
            .setMessage(logMessageText)
            .setNegativeButton(resources.getString(R.string.decline)) { _, _ ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                onLogout()
            }.show()
    }

    private fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

}