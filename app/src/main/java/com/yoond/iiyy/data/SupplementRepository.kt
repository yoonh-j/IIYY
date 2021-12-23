package com.yoond.iiyy.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupplementRepository @Inject constructor(
    private val supplementDao: SupplementDao
) {
    fun getAllSupplements() = supplementDao.getAllSupplements()

    fun getTodaySupplements(startTimeInMillis: Long, endTimeInMillis: Long) =
        supplementDao.getTodaySupplements(startTimeInMillis, endTimeInMillis)

    fun getSupplement(id: String) = supplementDao.getSupplement(id)

    fun deleteSupplement(supplement: Supplement) = supplementDao.deleteSupplement(supplement)

    fun updateSupplement(supplement: Supplement) = supplementDao.updateSupplement(supplement)
}