package com.arpansircar.notes_app.common

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun convertMillisToDateTime(timeInMillis: Long): String {
        val dateFormat = "hh:mm a | dd MMM"
        val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val calendar = Calendar.getInstance().also { it.timeInMillis = timeInMillis }
        return formatter.format(calendar.time).lowercase()
    }
}