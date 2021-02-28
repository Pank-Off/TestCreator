package ru.punkoff.testforeveryone.ui.fragments.creator

sealed class CreateTestState {
    object StartScreen : CreateTestState()
    object EmptyTitle : CreateTestState()
    object MaxCountTitleError : CreateTestState()
    object EmptyDescription : CreateTestState()
    object MaxCountDescriptionError : CreateTestState()
    object ErrorRadioButton : CreateTestState()
    object SuccessCreate : CreateTestState()
}