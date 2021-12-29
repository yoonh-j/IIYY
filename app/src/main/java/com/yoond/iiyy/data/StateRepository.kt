package com.yoond.iiyy.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateRepository @Inject constructor(
    private val stateDao: StateDao
) {
    fun getAllStates() = stateDao.getAllStates()

    fun insertState(state: State) = stateDao.insertState(state)
}