package com.raj.task.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.raj.task.R
import com.raj.task.data.model.CallLogObject
import com.raj.task.databinding.CallLogListAdapterBinding
import javax.inject.Inject

class CallLogAdapter @Inject constructor(
) : RecyclerView.Adapter<CallLogAdapter.ViewHolder>() {
    private var callLogObjectArrayList: ArrayList<CallLogObject> = ArrayList()
    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: CallLogListAdapterBinding =
            CallLogListAdapterBinding.bind(itemView)

        fun bind(
            callLogObject: CallLogObject,
            context: Context
        ) {
            binding.textViewName.text = callLogObject.name
            binding.textViewNumber.text = callLogObject.number
            val callDetails =
                callLogObject.date + " " + callLogObject.type + " : " + callLogObject.duration
            binding.textViewCallDetails.text = callDetails
            if (callLogObject.type == "Missed")
                binding.textViewName.setTextColor(ContextCompat.getColor(context, R.color.red))
            else
                binding.textViewName.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.call_log_list_adapter, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return callLogObjectArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            callLogObjectArrayList[holder.adapterPosition],
            context
        )
    }

    fun addData(
        callLogObjectArrayList: ArrayList<CallLogObject>,
        context: Context
    ) {
        this.callLogObjectArrayList.apply {
            this.clear()
            addAll(callLogObjectArrayList)
        }
        this.context = context
    }
}