package ru.punkoff.testforeveryone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val question: String,
    val answers: LinkedHashMap<String, String?>,
) : Parcelable