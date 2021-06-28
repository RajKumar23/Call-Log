package com.raj.task.data.room

import com.raj.task.data.model.CallLogObject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) :
    DatabaseHelper {
    override fun getAllCallLog(): Flow<List<CallLogObject>> =
        appDatabase.getCallLogDao().getAllCallLog()

    override suspend fun getLastCallLog(): CallLogObject =
        appDatabase.getCallLogDao().getLastCallLog()

    override suspend fun insertCallLog(callLog: CallLogObject) {
        appDatabase.getCallLogDao().insertCallLog(callLog)
    }
}