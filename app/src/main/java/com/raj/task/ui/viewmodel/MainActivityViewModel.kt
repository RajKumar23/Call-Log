package com.raj.task.ui.viewmodel

import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raj.task.data.model.CallLogObject
import com.raj.task.data.repository.MainActivityRepository
import com.raj.task.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: MainActivityRepository
) : ViewModel() {

    private val callLogMutableState =
        MutableStateFlow<Resource<ArrayList<CallLogObject>>>(Resource.loading(null))

    fun fetchCallLogFromDB(context: Context) {
        callLogMutableState.value = Resource.loading(ArrayList())
        viewModelScope.launch {
            try {
                val dbValue = repository.getLastCallLog()
                if (dbValue == null) {
                    fetchCallLogsFromDevice(context, null).join()
                } else {
                    fetchCallLogsFromDevice(
                        context,
                        CallLog.Calls._ID + ">" + dbValue.id
                    ).join()
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
                callLogMutableState.value =
                    Resource.error("Something Went Wrong. Please try again later", data = null)
            }

            repository.getAllCallLog().collect { callLogList ->
                callLogMutableState.value =
                    Resource.success(ArrayList(callLogList))
            }
        }
    }

    private fun fetchCallLogsFromDevice(context: Context, condition: String?): Job {
        return GlobalScope.launch {
            val projection = arrayOf(
                CallLog.Calls.NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE,
                CallLog.Calls._ID,
                CallLog.Calls.DATE
            )
            val cursor: Cursor? = context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                condition,
                null,
                CallLog.Calls._ID + " DESC"
            )
            if (cursor != null) {
                val cursorName =
                    cursor.getColumnIndex(CallLog.Calls.CACHED_NAME) // for name
                while (cursor.moveToNext()) {
                    val number: String =
                        cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER))
                    val name: String = if (cursor.getString(cursorName) != null)
                        cursor.getString(cursorName)
                    else
                        "Unknown"
                    val idString: String =
                        cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID))
                    val callType: String =
                        cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE))
                    val callDate: String =
                        cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE))
                    val callDayTime = Date(callDate.toLong())
                    val callDuration: String =
                        cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION))
                    val cursorTypeString: String = when (callType.toInt()) {
                        CallLog.Calls.OUTGOING_TYPE -> "Outgoing"
                        CallLog.Calls.INCOMING_TYPE -> "Incoming"
                        CallLog.Calls.MISSED_TYPE -> "Missed"
                        CallLog.Calls.REJECTED_TYPE -> "Rejected"
                        else -> "-"
                    }
                    insertProduct(
                        CallLogObject(
                            id = idString,
                            name = name,
                            number = number,
                            type = cursorTypeString,
                            date = dateToString(callDayTime, "dd-MM-yyy HH:mm:ss"),
                            duration = "$callDuration sec"
                        )
                    )
                }
                cursor.close()
            }
        }
    }

    private fun insertProduct(callLogObject: CallLogObject) {
        viewModelScope.launch {
            try {
                repository.insertCallLog(callLogObject)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun readCallLog(): StateFlow<Resource<ArrayList<CallLogObject>>> {
        return callLogMutableState
    }

    private fun dateToString(date: Date, format: String): String =
        SimpleDateFormat(format, Locale.getDefault()).format(date)
}