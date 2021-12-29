package com.yoond.iiyy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplements")
data class Supplement(
    @PrimaryKey
    @ColumnInfo(name = "timeInMillis")
    var timeInMillis: Long,
    val name: String,
    var status: Boolean
) {
    constructor(name: String, timeInMillis: Long, status: Boolean): this(timeInMillis, name, status)

    override fun toString() = "timeInMillis:$timeInMillis name:$name status:$status"
}