package com.kania.aquaninjanew

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class AddReminderActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var reminderId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        val etTime = findViewById<EditText>(R.id.et_time)
        val etVolume = findViewById<EditText>(R.id.et_volume)
        val btnSave = findViewById<Button>(R.id.btn_save)

        // Cek apakah ini mode edit
        reminderId = intent.getStringExtra("id")
        if (reminderId != null) {
            etTime.setText(intent.getStringExtra("time"))
            etVolume.setText(intent.getStringExtra("volume"))
            btnSave.text = "Update"
        }

        btnSave.setOnClickListener {
            val time = etTime.text.toString()
            val volume = etVolume.text.toString()

            val data = hashMapOf(
                "time" to time,
                "volume" to volume
            )

            if (reminderId != null) {
                // Mode Edit
                db.collection("reminders").document(reminderId!!)
                    .set(data)
                    .addOnSuccessListener {
                        finish()
                    }
            } else {
                // Mode Tambah
                db.collection("reminders")
                    .add(data)
                    .addOnSuccessListener {
                        finish()
                    }
            }
        }
    }
}
