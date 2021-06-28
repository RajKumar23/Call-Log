package com.raj.task.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.raj.task.data.model.CallLogObject
import kotlinx.coroutines.flow.Flow

@Dao
interface CallLogDao {
    @Query("SELECT * FROM CallLogObject")
    fun getAllCallLog(): Flow<List<CallLogObject>>

    @Query("SELECT * FROM CallLogObject LIMIT 1")
    suspend fun getLastCallLog(): CallLogObject

    @Insert(onConflict = REPLACE)
    suspend fun insertCallLog(product: CallLogObject)
}