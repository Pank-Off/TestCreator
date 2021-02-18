package ru.punkoff.testforeveryone.ui.fragments.your_tests.play_test.adapter

fun interface Listener {
    fun onClick(answer: String, score: String?)
}