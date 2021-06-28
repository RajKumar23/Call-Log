package com.raj.task.data.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raj.task.data.model.CallLogObject

class CallLogObjectConverter {
    @TypeConverter
    fun fromString(value: String): List<CallLogObject> {
        val listType = object : TypeToken<List<CallLogObject>>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<CallLogObject>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}