package com.raj.task.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raj.task.data.room.dao.CallLogDao
import com.raj.task.data.model.CallLogObject


@Database(
    entities = [CallLogObject::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCallLogDao(): CallLogDao
}