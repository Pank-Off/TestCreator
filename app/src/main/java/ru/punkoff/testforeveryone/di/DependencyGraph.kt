package ru.punkoff.testforeveryone.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.punkoff.testforeveryone.data.TempResult
import ru.punkoff.testforeveryone.data.TempTest
import ru.punkoff.testforeveryone.data.local.DatabaseProvider
import ru.punkoff.testforeveryone.data.local.LocalDatabase
import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.ui.all_tests.AllTestsViewModel
import ru.punkoff.testforeveryone.ui.creator.CreatorViewModel
import ru.punkoff.testforeveryone.ui.creator.questions.CreateQuestionsViewModel
import ru.punkoff.testforeveryone.ui.creator.results.CreateResultsViewModel
import ru.punkoff.testforeveryone.ui.notifications.NotificationsViewModel
import ru.punkoff.testforeveryone.ui.rate_us.RateUsViewModel
import ru.punkoff.testforeveryone.ui.settings.SettingsViewModel
import ru.punkoff.testforeveryone.ui.share_about_us.ShareAboutUsViewModel
import ru.punkoff.testforeveryone.ui.your_results.YourResultsViewModel
import ru.punkoff.testforeveryone.ui.your_tests.YourTestsViewModel
import ru.punkoff.testforeveryone.ui.your_tests.play_test.result.ShowResultViewModel
import ru.punkoff.testforeveryone.ui.your_tests.play_test.test.TestViewModel

object DependencyGraph {
    private val viewModelModule by lazy {
        module {
            viewModel { AllTestsViewModel() }
            viewModel { (test: TempTest) ->
                CreateQuestionsViewModel(test)
            }
            viewModel { (test: TempTest) ->
                CreateResultsViewModel(get(), test)
            }
            viewModel { CreatorViewModel() }
            viewModel { NotificationsViewModel() }
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
        }
    }

    private val dataBaseModule by lazy {
        module {
            single { LocalDatabase() } bind DatabaseProvider::class
        }
    }

    val modules = listOf(viewModelModule, dataBaseModule)
}