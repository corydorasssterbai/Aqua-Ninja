package com.kania.aquaninjanew.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kania.aquaninjanew.AddReminderActivity
import com.kania.aquaninjanew.R

class ReminderAdapter(
    private val reminderList: ArrayList<Reminder>,
    private val context: Context
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tv_time)
        val tvVolume: TextView = view.findViewById(R.id.tv_volume)
        val btnEdit: ImageView = view.findViewById(R.id.btn_edit)
        val btnDelete: ImageView = view.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminderList[position]
        holder.tvTime.text = "Jam: ${reminder.time}"
        holder.tvVolume.text = "Volume: ${reminder.volume} ml"

        // Tombol Edit
        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, AddReminderActivity::class.java)
            intent.putExtra("id", reminder.id)
            intent.putExtra("time", reminder.time)
            intent.putExtra("volume", reminder.volume)
            context.startActivity(intent)
        }

        // Tombol Delete
        holder.btnDelete.setOnClickListener {
            FirebaseFirestore.getInstance().collection("reminders")
                .document(reminder.id).delete()
                .addOnSuccessListener {
                    reminderList.removeAt(position)
                    notifyItemRemoved(position)
                }
        }
    }

    override fun getItemCount(): Int = reminderList.size
}
