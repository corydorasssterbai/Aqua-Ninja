package com.kania.aquaninjanew.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kania.aquaninjanew.R
import com.kania.aquaninjanew.model.Reminder

class ReminderAdapter(
    private val reminders: List<Reminder>,
    private val onEdit: (Reminder) -> Unit,
    private val onDelete: (Reminder) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_amount)
        val btnEdit: Button = itemView.findViewById(R.id.btn_edit)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = reminders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.tvTime.text = reminder.time
        holder.tvAmount.text = reminder.amount

        holder.btnEdit.setOnClickListener {
            onEdit(reminder)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(reminder)
        }
    }
}

