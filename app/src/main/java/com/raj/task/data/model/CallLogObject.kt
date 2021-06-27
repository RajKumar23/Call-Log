package com.raj.task.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["id"])
class CallLogObject(
    val id : String = "",
    val name: String? = null,
    val number: String? = null,
    val type: String? = null,
    val date: String? = null,
    val duration: String? = null
)