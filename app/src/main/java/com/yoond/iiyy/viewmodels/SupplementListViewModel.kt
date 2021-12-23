package com.yoond.iiyy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.data.SupplementRepository
import com.yoond.iiyy.utils.DateInMillisUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [HomeFragment]
 */
@HiltViewModel
class SupplementListViewModel @Inject constructor(
    private val repository: SupplementRepository
) : ViewModel() {
    val supplements: LiveData<List<Supplement>> =
        repository.getTodaySupplements(
            DateInMillisUtil.getTodayStartInMillis(),
            DateInMillisUtil.getTomorrowStartInMillis()
        )

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