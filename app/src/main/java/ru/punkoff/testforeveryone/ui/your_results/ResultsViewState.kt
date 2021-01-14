package ru.punkoff.testforeveryone.ui.your_results

import ru.punkoff.testforeveryone.data.local.room.ResultEntity

sealed class ResultsViewState {
    data class Value(val tests: List<ResultEntity>) : ResultsViewState()
    object EMPTY : ResultsViewState()
}