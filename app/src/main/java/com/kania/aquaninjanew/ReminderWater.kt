package com.kania.aquaninjanew

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.widget.EditText
import android.widget.Button


class ReminderWater : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminderwater)

        val startEditText = findViewById<EditText>(R.id.startwater)
        val finishEditText = findViewById<EditText>(R.id.finishwater)
        val intervalEditText = findViewById<EditText>(R.id.interval)
        val continueButton = findViewById<Button>(R.id.button)



        startEditText.setOnClickListener {
            showTimePicker(startEditText)
        }

        finishEditText.setOnClickListener {
            showTimePicker(finishEditText)
        }

        continueButton.setOnClickListener {
            val startTime = startEditText.text.toString()
            val finishTime = finishEditText.text.toString()
            val intervalStr = intervalEditText.text.toString()

            if (startTime.isEmpty() || finishTime.isEmpty() || intervalStr.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intervalMinutes = intervalStr.toIntOrNull()
            if (intervalMinutes == null || intervalMinutes <= 0) {
                Toast.makeText(this, "Interval harus lebih dari 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Reminder diset dari $startTime ke $finishTime setiap $intervalMinutes menit", Toast.LENGTH_LONG).show()
        }
    }

    private fun showTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            editText.setText(formattedTime)
        }, hour, minute, true)

        timePickerDialog.show()
    }
}
