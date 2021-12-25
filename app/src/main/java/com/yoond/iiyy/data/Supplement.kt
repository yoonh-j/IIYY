package com.yoond.iiyy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplements")
data class Supplement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    val name: String,
    val timeInMillis: Long,
    var status: Boolean
) {
    constructor(name: String, timeInMillis: Long, status: Boolean): this(0, name, timeInMillis, status)

    override fun toString() = "id:$id name:$name timeInMillis:$timeInMillis status:$status"
}