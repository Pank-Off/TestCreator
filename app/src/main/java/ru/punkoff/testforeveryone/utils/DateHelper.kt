package ru.punkoff.testforeveryone.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun parseDate(): String {
        val saveTime = Calendar.getInstance().time
        Log.d(javaClass.simpleName, "saveTime $saveTime")
        val simpleDateFormat = SimpleDateFormat("\ndd.MM.yyyy, H:mm", Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getTimeZone("Europe/Moscow")
        val dateString = simpleDateFormat.format(saveTime)
        Log.d(javaClass.simpleName, "dateString $dateString")
        return dateString
    }
}