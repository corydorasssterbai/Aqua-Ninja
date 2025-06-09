package com.kania.aquaninjanew

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        val fullName = findViewById<EditText>(R.id.full_name)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val password = findViewById<EditText>(R.id.password)
        val createAccountButton = findViewById<Button>(R.id.create_account)
        val loginText = findViewById<TextView>(R.id.login)

        // Jika user sudah login & sudah verifikasi → langsung ke Home
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            startActivity(Intent(this, Home::class.java))
            finish()
        }

        createAccountButton.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { verifyTask ->
                                if (verifyTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Akun berhasil dibuat! Silakan verifikasi lewat email.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    auth.signOut()
                                    startActivity(Intent(this, Login::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Gagal kirim verifikasi: ${verifyTask.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Gagal daftar: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        loginText.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}
