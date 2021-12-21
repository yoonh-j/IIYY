package com.yoond.iiyy.data

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun calendarToTimeInMillis(cal: Calendar): Long = cal.timeInMillis

    @TypeConverter
    fun timeInMillisToCalendar(_timeInMillis: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = _timeInMillis }
}