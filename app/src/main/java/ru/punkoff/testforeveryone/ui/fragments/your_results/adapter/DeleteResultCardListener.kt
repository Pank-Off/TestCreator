package ru.punkoff.testforeveryone.ui.fragments.your_results.adapter

import ru.punkoff.testforeveryone.data.local.room.ResultEntity

fun interface DeleteResultCardListener {
    fun onClick(test: ResultEntity)
}