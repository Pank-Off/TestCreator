package ru.punkoff.testforeveryone.ui.your_tests.adapter

import ru.punkoff.testforeveryone.data.local.room.TestEntity

fun interface Listener {
    fun onClick(test: TestEntity)
}