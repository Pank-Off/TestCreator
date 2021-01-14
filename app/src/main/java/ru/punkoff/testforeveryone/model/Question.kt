package ru.punkoff.testforeveryone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val question: String,
    val answers: HashMap<String, String?>,
//    val answerTwo: HashMap<String, Int>,
//    val answerThree: HashMap<String, Int>
) : Parcelable