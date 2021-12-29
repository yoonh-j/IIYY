package com.yoond.iiyy.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.yoond.iiyy.data.State
import com.yoond.iiyy.data.StateRepository
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.data.SupplementRepository
import com.yoond.iiyy.utils.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

@HiltWorker
class DatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val supRepository: SupplementRepository,
    private val stateRepository: StateRepository
): CoroutineWorker(appContext, workerParams) {
    private var allTaken = true
    private var partiallyTaken = false
    private val stateTimeInMillis = getTodayStartInMillis()

    /**
     * 영양제 섭취 상태를 모두 확인하고 db 업데이트.
     * 현재 db의 Supplement 목록의 상태를 모두 확인하고 totalState 결정.
     */
    override suspend fun doWork(): Result = coroutineScope {
        try {
            withContext(Dispatchers.IO) {
                supRepository.getSupplementsByTimeInMillis(stateTimeInMillis)
                    .forEach { supplement ->
                        if (supplement.status) {
                            partiallyTaken = true
                        } else {
                            allTaken = false
                        }
                        insertSupplement(supplement)
                    }
                insertState()
                Result.success()
            }
        } catch (e: Exception) {
            Log.e(WORKER_NAME, "worker failed", e)
            Result.failure()
        } finally {
            initWorkManager(applicationContext)
        }
    }

    /**
     * Supplement의 timeInMillis를 내일로 설정하고 db 업데이트.
     */
    private fun insertSupplement(supplement: Supplement) {
        Log.d("DATABASE_WORKER", supplement.toString())
        supplement.timeInMillis += DAY_IN_MILLIS
        supplement.status = false
        Log.d("DATABASE_WORKER", supplement.toString())
        supRepository.insertSupplement(supplement)
    }

    /**
     * 오늘 00시 00분 timeInMillis를 key로 하는 State 객체를 생성하고 db 삽입.
     */
    private fun insertState() {
        val state: State = if (allTaken) {
            State(stateTimeInMillis, ALL_TAKEN)
        } else if (partiallyTaken) {
            State(stateTimeInMillis, PARTIALLY_TAKEN)
        } else {
            State(stateTimeInMillis, NOT_TAKEN)
        }
        stateRepository.insertState(state)
    }
}