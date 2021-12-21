package com.yoond.iiyy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yoond.iiyy.data.Supplement
import com.yoond.iiyy.data.SupplementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for [HomeFragment]
 */
@HiltViewModel
class SupplementListViewModel @Inject internal constructor(
    repository: SupplementRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val supplements: LiveData<List<Supplement>> = repository.getAllSupplements()
}