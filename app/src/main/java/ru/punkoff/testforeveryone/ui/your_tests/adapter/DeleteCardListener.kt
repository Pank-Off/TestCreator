package ru.punkoff.testforeveryone.ui.your_tests.adapter

import ru.punkoff.testforeveryone.data.local.room.TestEntity

fun interface DeleteCardListener {
    fun onClick(test: TestEntity)
}