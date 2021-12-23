package com.yoond.iiyy.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StateDao {
    @Insert
    fun insertState(state: State)

    @Insert
    fun insertStateList(list: List<State>)

    @Delete
    fun deleteState(state: State)

    @Update
    fun updateState(state: State)

    @Query("select * from states")
    fun getAllStates(): LiveData<List<State>>
}