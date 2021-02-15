package ru.punkoff.testforeveryone.model

import android.os.Parcelable
import io.perfmark.Link
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val question: String,
    val answers: HashMap<String, String?>,
) : Parcelable {
    constructor() : this("", LinkedHashMap())
}