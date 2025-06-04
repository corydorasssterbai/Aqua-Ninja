package com.kania.aquaninjanew.model

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kania.aquaninjanew.R

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: String = "",
    val age: Int = 0,
    val gambar: String = "",
    val imageUrl: String = ""
)
