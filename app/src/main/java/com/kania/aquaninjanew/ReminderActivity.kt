package com.kania.aquaninjanew

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kania.aquaninjanew.Adapter.ReminderAdapter
import com.kania.aquaninjanew.model.Reminder

class ReminderActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ReminderAdapter
    private lateinit var reminderList: ArrayList<Reminder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder) // PASTIKAN INI BENER

        db = FirebaseFirestore.getInstance()
        reminderList = arrayListOf()

        adapter = ReminderAdapter(reminderList,
            onEdit = { reminder -> editReminder(reminder) },
            onDelete = { reminder -> deleteReminder(reminder) }
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rv_reminders)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fetchReminders() // tampilkan data dari firebase

        val btnAdd = findViewById<Button>(R.id.btn_add)
        val etTime = findViewById<EditText>(R.id.et_time)
        val etAmount = findViewById<EditText>(R.id.et_amount)

        btnAdd.setOnClickListener {
            val time = etTime.text.toString()
            val amount = etAmount.text.toString()

            if (time.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Isi semua field dulu ya!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = db.collection("reminders").document().id
            val reminder = Reminder(id, time, amount)

            db.collection("reminders").document(id).set(reminder)
                .addOnSuccessListener {
                    Toast.makeText(this, "Reminder berhasil ditambah!", Toast.LENGTH_SHORT).show()
                    fetchReminders() // refresh list
                    etTime.text.clear()
                    etAmount.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal nambah data!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun fetchReminders() {
        db.collection("reminders").get()
            .addOnSuccessListener {
                reminderList.clear()
                for (doc in it) {
                    val reminder = doc.toObject(Reminder::class.java)
                    reminderList.add(reminder)
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun editReminder(reminder: Reminder) {
        db.collection("reminders").document(reminder.id)
            .update("time", reminder.time, "amount", reminder.amount)
            .addOnSuccessListener {
                Toast.makeText(this, "Reminder diupdate", Toast.LENGTH_SHORT).show()
                fetchReminders()
            }
    }

    private fun deleteReminder(reminder: Reminder) {
        db.collection("reminders").document(reminder.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Reminder dihapus", Toast.LENGTH_SHORT).show()
                fetchReminders()
            }
    }
}
