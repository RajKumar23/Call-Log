package com.raj.task.data.room

import com.raj.task.data.model.CallLogObject

interface DatabaseHelper {
    suspend fun getAllCallLog(): List<CallLogObject>

    suspend fun insertCallLog(callLog: CallLogObject)
}