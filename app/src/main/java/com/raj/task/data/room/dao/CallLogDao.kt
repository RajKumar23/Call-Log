package com.raj.task.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raj.task.data.model.CallLogObject

@Dao
interface CallLogDao {
    @Query("SELECT * FROM CallLogObject")
    suspend fun getAllCallLog(): List<CallLogObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCallLog(product: CallLogObject)
}