package ru.punkoff.testforeveryone.ui.all_tests

import ru.punkoff.testforeveryone.model.Test

sealed class TestsViewState {
    data class Value(val tests: List<Test>) : TestsViewState()
    object EMPTY : TestsViewState()
}