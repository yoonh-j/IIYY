package com.yoond.iiyy.utils

import java.util.*

class DateInMillisUtil {
    companion object {
        fun getTodayStartInMillis(): Long {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)

            return cal.timeInMillis
        }

        fun getTomorrowStartInMillis(): Long {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, 1)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)

            return cal.timeInMillis
        }

        fun getYesterdayStartInMillis(): Long {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)

            return cal.timeInMillis
        }
    }
}
