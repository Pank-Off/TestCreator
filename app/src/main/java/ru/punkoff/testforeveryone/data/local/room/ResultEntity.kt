package ru.punkoff.testforeveryone.data.local.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "table_results")
@Parcelize
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String,
    val maxScore: Int,
    var score: Int,
    var color: Color = Color.values().toList().shuffled().first(),
) : Parcelable

