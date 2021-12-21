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

    @Query("select * from supplements where id = :id")
    fun getSupplement(id: String): LiveData<Supplement>
}