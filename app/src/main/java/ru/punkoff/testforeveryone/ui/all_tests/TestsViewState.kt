package ru.punkoff.testforeveryone.ui.all_tests

import ru.punkoff.testforeveryone.data.local.room.TestEntity

sealed class TestsViewState {
    data class Value(val tests: List<TestEntity>) : TestsViewState()
    object EMPTY : TestsViewState()
}