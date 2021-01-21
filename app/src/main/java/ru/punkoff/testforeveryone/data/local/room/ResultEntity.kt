package ru.punkoff.testforeveryone.data.local.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.random.Random

private val idRandom = Random(Calendar.getInstance().timeInMillis)
val idResult: Long
    get() = idRandom.nextLong()

@Entity(tableName = "table_results")
@Parcelize
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val testTitle: String,
    val title: String,
    val body: String,
    val maxScore: Int,
    var score: Int,
    var lastPlayData: String,
    var color: Color = Color.values().toList().shuffled().first(),
    val resultId: Long = idResult,
) : Parcelable

