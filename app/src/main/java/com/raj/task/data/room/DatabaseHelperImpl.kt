package com.raj.task.data.room

import com.raj.task.data.model.CallLogObject
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) :
    DatabaseHelper {
    override suspend fun getAllCallLog(): List<CallLogObject> =
        appDatabase.getCallLogDao().getAllCallLog()

    override suspend fun insertCallLog(callLog: CallLogObject) {
        appDatabase.getCallLogDao().insertCallLog(callLog)
    }
}