package ru.punkoff.testforeveryone

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.TempResult.Companion.EXTRA_TEMP_RESULT
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.data.TempTest.Companion.EXTRA_TEMP_TEST
import ru.punkoff.testforeveryone.ui.your_tests.play_test.result.ShowResultFragment
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestFragment
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestFragment.Companion.EXTRA_TEST

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                //  R.id.nav_all_tests,
                R.id.nav_your_tests,
                R.id.nav_results,
                R.id.nav_creator,
                // R.id.nav_notifications,
                R.id.nav_settings,
                R.id.nav_share_us,
                R.id.nav_rate_us
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
}