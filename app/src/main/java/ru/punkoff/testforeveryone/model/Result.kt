package ru.punkoff.testforeveryone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    val from: String,
    val to: String,
    val title: String,
    val description: String
) : Parcelable
