package ru.punkoff.testforeveryone.ui.activities

sealed class SplashViewState {
    class Error(val error: Throwable) : SplashViewState()
    object Auth : SplashViewState()
}