package com.yoond.iiyy.data

import com.yoond.iiyy.utils.DAY_IN_MILLIS
import com.yoond.iiyy.utils.getTodayStartInMillis
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupplementRepository @Inject constructor(
    private val supplementDao: SupplementDao
) {
    private val startTimeInMillis = getTodayStartInMillis()

    fun getTodaySupplements() =
        supplementDao.getTodaySupplements(startTimeInMillis, startTimeInMillis + DAY_IN_MILLIS)

    fun getSupplementsByTimeInMillis(startTimeInMillis: Long) =
        supplementDao.getSupplementsByTimeInMillis(startTimeInMillis, startTimeInMillis + DAY_IN_MILLIS)

    fun insertSupplement(supplement: Supplement) = supplementDao.insertSupplement(supplement)

    fun deleteSupplement(supplement: Supplement) = supplementDao.deleteSupplement(supplement)

    fun updateSupplement(supplement: Supplement) = supplementDao.updateSupplement(supplement)
}