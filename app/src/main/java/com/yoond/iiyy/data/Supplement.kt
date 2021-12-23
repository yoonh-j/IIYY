package com.yoond.iiyy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplements")
data class Supplement(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    val name: String,
    val timeInMillis: Long,
    var status: Boolean
) {
    override fun toString() = "id:$id name:$name timeInMillis:$timeInMillis status:$status"
}