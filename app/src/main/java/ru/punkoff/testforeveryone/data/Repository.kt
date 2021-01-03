package ru.punkoff.testforeveryone.data

import ru.punkoff.testforeveryone.data.local.room.TestEntity
import ru.punkoff.testforeveryone.model.Test

object Repository {
    var test = TestEntity("", "")

    val tests = listOf(
        Test(
            "TITLE 1",
            "BODY"
        ),
        Test(
            "TITLE 2",
            "BODY"
        ),
        Test(
            "TITLE 3",
            "BODY"
        ),
        Test(
            "TITLE 4",
            "BODY"
        ),
        Test(
            "TITLE 5",
            "BODY"
        )
    )

    fun createTest(title: String, body: String) {
        test = TestEntity(title, body)
    }
}