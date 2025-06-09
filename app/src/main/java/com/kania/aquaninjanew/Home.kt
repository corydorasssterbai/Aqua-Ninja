package com.kania.aquaninjanew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {

    private lateinit var btnAddGoal: Button
    private lateinit var btnAddGoal2: Button
    private lateinit var btnDashboard: Button

    private lateinit var navHome: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var navAlarm: ImageView
    private lateinit var navAnalysis: ImageView
    private lateinit var navSettings: ImageView

    private lateinit var tvName: TextView
    private lateinit var tvGreeting: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inisialisasi tombol dan elemen UI
        btnAddGoal = findViewById(R.id.btn_add_goal)
        btnAddGoal2 = findViewById(R.id.btn_add_goal2)
        btnDashboard = findViewById(R.id.btn_dashboard)

        navHome = findViewById(R.id.nav_home)
        navProfile = findViewById(R.id.nav_profile)
        navAlarm = findViewById(R.id.nav_alarm)
        navAnalysis = findViewById(R.id.nav_analysis)
        navSettings = findViewById(R.id.nav_settings)

        tvName = findViewById(R.id.tv_name)
        tvGreeting = findViewById(R.id.tv_greeting)

        // Contoh isi teks (bisa diambil dari Firestore juga kalau mau)
        tvGreeting.text = "Good Morning ,"
        tvName.text = "Jeonghan"

        // Aksi tombol goal
        btnAddGoal.setOnClickListener {
            // Tambahkan aksi sesuai kebutuhan (misalnya ke form input goal)
        }

        btnAddGoal2.setOnClickListener {
            // Aksi lain jika perlu
        }

        // Navigasi dashboard
        btnDashboard.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        navAlarm.setOnClickListener {
            // Tambahkan intent ke AlarmActivity kalau ada
            val btnAddReminder = findViewById<ImageView>(R.id.nav_alarm)
            btnAddReminder.setOnClickListener {
                startActivity(Intent(this, ReminderActivity::class.java))

            }
        }
    }
}