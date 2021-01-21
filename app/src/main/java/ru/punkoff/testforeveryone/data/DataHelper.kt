package ru.punkoff.testforeveryone.data

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DataHelper {
    fun parseDate(): String {
        val saveTime = Calendar.getInstance().time
        Log.d(javaClass.simpleName, "saveTime $saveTime")
        val simpleDateFormat = SimpleDateFormat("MMMM d yyyy", Locale.ENGLISH)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("Europe/Moscow")
        val dateString = simpleDateFormat.format(saveTime)
        Log.d(javaClass.simpleName, "dateString $dateString")
        return dateString
    }
}