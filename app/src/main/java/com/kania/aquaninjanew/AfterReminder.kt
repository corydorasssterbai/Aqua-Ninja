package com.kania.aquaninjanew

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class AfterReminder : AppCompatActivity() {

    private lateinit var tvProgress: TextView
    private lateinit var tvAthlete: TextView
    private lateinit var tvCognitive: TextView
    private lateinit var btnArchive: Button

    private val db = FirebaseFirestore.getInstance()
    private val goalRef = db.collection("goalPreview").document("user1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_after_reminder)

        // Handle insets (jika layout ada id 'main')
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind UI komponen
        tvProgress = findViewById(R.id.progressCircle)
        tvAthlete = findViewById(R.id.athletePerformance)
        tvCognitive = findViewById(R.id.cognitivePerformance)
        btnArchive = findViewById(R.id.goalButton)

        // Ambil data dari Firebase
        fetchGoalData()

        // Contoh simpan data (opsional: klik untuk testing)
        btnArchive.setOnClickListener {
            saveGoalData(100, true, 100, 100)
        }
    }

    private fun fetchGoalData() {
        goalRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val progress = document.getLong("progress")?.toInt() ?: 0
                val isArchived = document.getBoolean("isArchived") ?: false
                val athlete = document.getLong("athlete")?.toInt() ?: 0
                val cognitive = document.getLong("cognitive")?.toInt() ?: 0

                updateUI(progress, isArchived, athlete, cognitive)
            }
        }.addOnFailureListener {
            tvProgress.text = "Error"
        }
    }

    private fun updateUI(progress: Int, isArchived: Boolean, athlete: Int, cognitive: Int) {
        tvProgress.text = "$progress%"
        tvProgress.setTextColor(if (isArchived) Color.parseColor("#4CAF50") else Color.parseColor("#03A9F4"))

        tvAthlete.text = "Athlete Performance\n${if (athlete >= 0) "+$athlete%" else "$athlete%"}"
        tvAthlete.setTextColor(if (athlete >= 0) Color.parseColor("#4CAF50") else Color.parseColor("#F44336"))

        tvCognitive.text = "Cognitive Performance\n${if (cognitive >= 0) "+$cognitive%" else "$cognitive%"}"
        tvCognitive.setTextColor(if (cognitive >= 0) Color.parseColor("#4CAF50") else Color.parseColor("#F44336"))

        btnArchive.text = if (isArchived) "Goal Archive" else "Goal Not Archive"
    }

    private fun saveGoalData(progress: Int, isArchived: Boolean, athlete: Int, cognitive: Int) {
        val data = hashMapOf(
            "progress" to progress,
            "isArchived" to isArchived,
            "athlete" to athlete,
            "cognitive" to cognitive
        )
        goalRef.set(data)
    }
}
