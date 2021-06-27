package com.raj.task.data.repository

import com.raj.task.data.model.CallLogObject
import com.raj.task.data.room.DatabaseHelper
import javax.inject.Inject

class MainActivityRepository @Inject constructor(
    private val dbHelper: DatabaseHelper
) {
    suspend fun getAllCallLog() = dbHelper.getAllCallLog()

    suspend fun insertCallLog(callLogObject: CallLogObject) =
        dbHelper.insertCallLog(callLogObject)
}