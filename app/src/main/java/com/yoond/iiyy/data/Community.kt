package com.yoond.iiyy.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Community(
    var key: String = "",
    val uid: String,
    val title: String,
    val content: String,
    val timeInMillis: Long
): Parcelable {

    constructor(): this("", "", "", "", 0)

    override fun toString(): String {
        return "$uid $title $content $timeInMillis \n"
    }
}
