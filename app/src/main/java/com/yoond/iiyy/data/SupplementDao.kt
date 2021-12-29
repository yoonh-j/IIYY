package com.yoond.iiyy.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SupplementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSupplement(supplement: Supplement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSupplementList(list: List<Supplement>)

    @Update
    fun updateSupplement(supplement: Supplement)

    @Delete
    fun deleteSupplement(supplement: Supplement)

    @Query("select * from supplements where timeInMillis >= :startTimeInMillis and timeInMillis < :endTimeInMillis order by timeInMillis")
    fun getTodaySupplements(startTimeInMillis: Long, endTimeInMillis: Long): LiveData<List<Supplement>>

    @Query("select * from supplements where timeInMillis >= :startTimeInMillis and timeInMillis < :endTimeInMillis order by timeInMillis")
    fun getSupplementsByTimeInMillis(startTimeInMillis: Long, endTimeInMillis: Long): List<Supplement>

    @Query("select status from supplements where timeInMillis >= :startTimeInMillis and timeInMillis < :endTimeInMillis")
    fun getTotalState(startTimeInMillis: Long, endTimeInMillis: Long): LiveData<Boolean>
}