package ru.punkoff.testforeveryone.ui.adapter

import ru.punkoff.testforeveryone.model.Test

fun interface Listener {
    fun onClick(test: Test)
}