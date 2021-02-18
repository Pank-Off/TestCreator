package ru.punkoff.testforeveryone.ui.fragments.your_tests

import ru.punkoff.testforeveryone.data.local.room.TestEntity

sealed class TestsViewState {
    data class Value(val tests: List<TestEntity>) : TestsViewState()
    object Loading : TestsViewState()
    object EMPTY : TestsViewState()
}