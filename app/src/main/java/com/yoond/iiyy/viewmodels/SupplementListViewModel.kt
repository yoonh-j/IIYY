package com.yoond.iiyy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.data.SupplementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * ViewModel for [HomeFragment]
 */
@HiltViewModel
class SupplementListViewModel @Inject constructor(
    private val repository: SupplementRepository
) : ViewModel() {
    val supplements: LiveData<List<Supplement>> = repository.getAllSupplements()

    suspend fun getSupplementsByTimeInMillis(timeInMillis: Long): List<Supplement> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            repository.getSupplementsByTimeInMillis(timeInMillis)
        }

    fun deleteSupplement(supplement: Supplement) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteSupplement(supplement)
        }
    }

    fun updateSupplement(supplement: Supplement) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateSupplement(supplement)
        }
    }
}