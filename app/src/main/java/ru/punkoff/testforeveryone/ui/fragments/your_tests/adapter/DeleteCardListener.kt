package ru.punkoff.testforeveryone.ui.fragments.your_tests.adapter

import ru.punkoff.testforeveryone.data.local.room.TestEntity

fun interface DeleteCardListener {
    fun onClick(test: TestEntity)
}