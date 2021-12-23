package com.yoond.iiyy.viewmodels

import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.yoond.iiyy.data.State
import com.yoond.iiyy.data.StateRepository
import com.yoond.iiyy.utils.ALL_TAKEN
import com.yoond.iiyy.utils.NOT_TAKEN
import com.yoond.iiyy.utils.PARTIALLY_TAKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: StateRepository
): ViewModel() {
    val states = repository.getAllStates()
    val allTakenList = mutableListOf<CalendarDay>()
    val partiallyTakenList = mutableListOf<CalendarDay>()
    val notTakenList = mutableListOf<CalendarDay>()

    fun initTakenLists(stateList: List<State>) {
        CoroutineScope(Dispatchers.IO).launch {
            for (state in stateList) {
                val cal = Calendar.getInstance()
                cal.timeInMillis = state.timeInMillis
                val day = CalendarDay.from(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH)
                )

                when (state.totalState) {
                    ALL_TAKEN -> {
                        allTakenList.add(day)
                    }
                    PARTIALLY_TAKEN -> {
                        partiallyTakenList.add(day)
                    }
                    NOT_TAKEN -> {
                        notTakenList.add(day)
                    }
                }
            }
        }
    }
}