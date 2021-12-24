package com.yoond.iiyy.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SupplementDao {
    @Insert
    fun insertSupplement(supplement: Supplement)

    @Insert
    fun insertSupplementList(list: List<Supplement>)

    @Update
    fun updateSupplement(supplement: Supplement)

    @Delete
    fun deleteSupplement(supplement: Supplement)

    @Query("select * from supplements order by timeInMillis")
    fun getAllSupplements(): LiveData<List<Supplement>>

    @Query("select * from supplements where timeInMillis >= :startTimeInMillis and timeInMillis < :endTimeInMillis")
    fun getSupplementsByTimeInMillis(startTimeInMillis: Long, endTimeInMillis: Long): List<Supplement>

    @Query("select * from supplements where id = :id")
    fun getSupplement(id: String): LiveData<Supplement>

    @Query("select status from supplements where timeInMillis >= :startTimeInMillis and timeInMillis < :endTimeInMillis")
    fun getTotalState(startTimeInMillis: Long, endTimeInMillis: Long): LiveData<Boolean>
}