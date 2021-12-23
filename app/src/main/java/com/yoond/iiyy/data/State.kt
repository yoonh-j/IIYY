package com.yoond.iiyy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "states")
class State(
    @PrimaryKey
    @ColumnInfo(name = "timeInMillis")
    val timeInMillis: Long,
    val totalState: Int
) {
    override fun toString(): String {
        return "timeInMillis:$timeInMillis totalState:$totalState"
    }
}