package ru.punkoff.testforeveryone.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.punkoff.testforeveryone.ui.activities.MainViewModel
import ru.punkoff.testforeveryone.ui.activities.SplashViewModel
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.data.local.DatabaseProvider
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.data.remote.FirebaseDatabaseProvider
import ru.punkoff.testforeveryone.data.remote.FirebaseProvider
import ru.punkoff.testforeveryone.ui.fragments.all_tests.AllTestsViewModel
import ru.punkoff.testforeveryone.ui.fragments.creator.CreatorViewModel
import ru.punkoff.testforeveryone.ui.fragments.creator.questions.CreateQuestionsViewModel
import ru.punkoff.testforeveryone.ui.fragments.creator.results.CreateResultsViewModel
import ru.punkoff.testforeveryone.ui.fragments.google_auth.GoogleAuthViewModel
import ru.punkoff.testforeveryone.ui.fragments.rate_us.RateUsViewModel
import ru.punkoff.testforeveryone.ui.fragments.settings.SettingsViewModel
import ru.punkoff.testforeveryone.ui.fragments.share_about_us.ShareAboutUsViewModel
import ru.punkoff.testforeveryone.ui.fragments.your_results.YourResultsViewModel
import ru.punkoff.testforeveryone.ui.fragments.your_tests.YourTestsViewModel
import ru.punkoff.testforeveryone.ui.fragments.your_tests.play_test.result.ShowResultViewModel
import ru.punkoff.testforeveryone.ui.fragments.your_tests.play_test.test.TestViewModel

object DependencyGraph {

    private val fireStoreDatabaseProviderModule by lazy {
        module {
            single { FirebaseFirestore.getInstance() }
            single { FirebaseAuth.getInstance() }
        }
    }
    private val viewModelModule by lazy {
        module {
            viewModel { AllTestsViewModel(get()) }
            viewModel { (test: TempTest) ->
                CreateQuestionsViewModel(test)
            }
            viewModel { (test: TempTest) ->
                CreateResultsViewModel(get(), get(), test)
            }
            viewModel { CreatorViewModel() }
            viewModel { GoogleAuthViewModel() }
            viewModel { RateUsViewModel() }
            viewModel { SettingsViewModel() }
            viewModel { ShareAboutUsViewModel() }
            viewModel { YourResultsViewModel(get()) }
            viewModel { YourTestsViewModel(get()) }
            viewModel { (test: TestEntity?) ->
                TestViewModel(test)
            }
            viewModel { (result: TempResult) ->
                ShowResultViewModel(get(), result)
            }

            viewModel { SplashViewModel() }
            viewModel { MainViewModel() }
        }
    }

    private val dataBaseModule by lazy {
        module {
            single { LocalDatabase() } bind DatabaseProvider::class
            single { FirebaseDatabaseProvider(get(), get()) } bind FirebaseProvider::class
        }
    }

    val modules = listOf(viewModelModule, dataBaseModule, fireStoreDatabaseProviderModule)
}