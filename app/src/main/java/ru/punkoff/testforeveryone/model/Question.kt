package ru.punkoff.testforeveryone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val question: String,
    val answerOne: String,
    val answerTwo: String,
    val answerThree: String
) : Parcelable