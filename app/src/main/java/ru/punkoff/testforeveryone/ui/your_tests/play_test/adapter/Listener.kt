package ru.punkoff.testforeveryone.ui.your_tests.play_test.adapter

fun interface Listener {
    fun onClick(answer: String, score: String?)
}