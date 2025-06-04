package com.kania.aquaninjanew

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kania.aquaninjanew.adapter.ReminderAdapter

class ReminderActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReminderAdapter
    private val reminderList = ArrayList<Reminder>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        recyclerView = findViewById(R.id.rv_reminders)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ReminderAdapter(reminderList, this)
        recyclerView.adapter = adapter

        // Ambil data dari Firestore
        fetchReminders()

        // Intent ke tambah data
        findViewById<ImageView>(R.id.btn_add).setOnClickListener {
            startActivity(Intent(this, AddReminderActivity::class.java))
        }
    }

    private fun fetchReminders() {
        db.collection("reminders")
            .get()
            .addOnSuccessListener { result ->
                reminderList.clear()
                for (document in result) {
                    val reminder = document.toObject(Reminder::class.java)
                    reminder.id = document.id
                    reminderList.add(reminder)
                }
                adapter.notifyDataSetChanged()
            }
    }
}
