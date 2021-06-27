package com.raj.task.data.room


import android.content.Context
import androidx.room.Room

class DatabaseBuilder {
    private var mInstance: AppDatabase? = null

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "call-log").build()

    @Synchronized
    fun getInstance(context: Context): AppDatabase {
        if (mInstance == null) {
            mInstance = buildRoomDB(context)
        }
        return mInstance!!
    }

    fun destroyInstance() {
        mInstance = null
    }
}