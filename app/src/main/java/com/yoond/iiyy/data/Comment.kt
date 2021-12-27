package com.yoond.iiyy.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val key: String,
    val uid: String,
    val comment: String,
    val timeInMillis: Long
) : Parcelable {

    constructor(): this("", "", "", 0)

    override fun toString() = "$key $uid $comment $timeInMillis"
}
