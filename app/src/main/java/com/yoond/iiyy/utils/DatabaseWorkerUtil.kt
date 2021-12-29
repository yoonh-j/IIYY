package com.yoond.iiyy.utils

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yoond.iiyy.workers.DatabaseWorker
import java.util.*
import java.util.concurrent.TimeUnit


fun initWorkManager(appContext: Context) {
    val workRequest = OneTimeWorkRequestBuilder<DatabaseWorker>()
        .setInitialDelay(getDuration(), TimeUnit.MILLISECONDS)
        .build()
    WorkManager.getInstance(appContext)
        .enqueueUniqueWork(WORKER_NAME, ExistingWorkPolicy.REPLACE, workRequest)
}

private fun getDuration(): Long {
    val cur = Calendar.getInstance().timeInMillis
    val due = getTomorrowStartInMillis() - MINUTE_IN_MILLIS // 오늘 23시 59분
//    val due = Calendar.getInstance().timeInMillis + MINUTE_IN_MILLIS // 1분 뒤

    return due - cur
}