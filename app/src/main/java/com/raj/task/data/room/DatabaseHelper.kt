package com.raj.task.data.room

import com.raj.task.data.model.CallLogObject
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {
    fun getAllCallLog(): Flow<List<CallLogObject>>

    suspend fun getLastCallLog(): CallLogObject

    suspend fun insertCallLog(callLog: CallLogObject)
}