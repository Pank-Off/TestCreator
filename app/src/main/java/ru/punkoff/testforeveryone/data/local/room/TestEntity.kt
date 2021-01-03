package ru.punkoff.testforeveryone.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.punkoff.testforeveryone.model.Color

@Entity(tableName = "table_tests")
data class TestEntity(
    val title: String,
    val body: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}