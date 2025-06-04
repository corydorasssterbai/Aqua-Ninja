package com.kania.aquaninjanew

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.kania.aquaninjanew.model.User

class UserActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CHANGE_IMAGE = 1001
    }
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var etImageUrl: EditText
    private lateinit var profileImageView: ImageView
    private lateinit var genderGroup: RadioGroup
    private lateinit var btnSaveUser: Button
    private lateinit var btnDeleteUser: Button

    private val firestore = FirebaseFirestore.getInstance()
    private var userDocId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user2)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)
        etAge = findViewById(R.id.etAge)
        profileImageView = findViewById(R.id.profileImageView)
        genderGroup = findViewById(R.id.genderGroup)
        btnSaveUser = findViewById(R.id.btnSaveUser)
        btnDeleteUser = findViewById(R.id.btnDeleteUser)

        loadUserData()

        btnSaveUser.setOnClickListener {
            saveUser()
        }

        btnDeleteUser.setOnClickListener {
            deleteUser()
        }
        profileImageView.setOnClickListener {
            val intent = Intent(this, ChangeProfileImageActivity::class.java)
            startActivityForResult(intent, REQUEST_CHANGE_IMAGE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHANGE_IMAGE && resultCode == Activity.RESULT_OK) {
            val newImageUrl = data?.getStringExtra("image_url")
            newImageUrl?.let {
                etImageUrl.setText(it)
                Glide.with(this)
                    .load(it)
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .into(profileImageView)
            }
        }
    }

    private fun loadUserData() {
        firestore.collection("users").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val user = doc.toObject(User::class.java)
                userDocId = doc.id

                etFirstName.setText(user.firstName)
                etLastName.setText(user.lastName)
                etEmail.setText(user.email)
                etAge.setText(user.age.toString())
                etImageUrl.setText(user.imageUrl ?: "")

                when (user.gender.lowercase()) {
                    "male" -> genderGroup.check(R.id.rbMale)
                    "female" -> genderGroup.check(R.id.rbFemale)
                    "other" -> genderGroup.check(R.id.rbOther)
                }

                // Tampilkan gambar pakai Glide
                Glide.with(this)
                    .load(user.imageUrl)
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .into(profileImageView)

                break // hanya ambil 1 data user
            }
        }
    }

    private fun saveUser() {
        val selectedGenderId = genderGroup.checkedRadioButtonId
        val selectedGender = findViewById<RadioButton>(selectedGenderId)?.text.toString()

        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val ageText = etAge.text.toString().trim()
        val imageUrl = etImageUrl.text.toString().trim()

        // Validasi input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || selectedGenderId == -1 || ageText.isEmpty()) {
            Toast.makeText(this, "Harap isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            gender = selectedGender,
            age = ageText.toIntOrNull() ?: 0,
            imageUrl = imageUrl
        )

        if (userDocId != null) {
            firestore.collection("users").document(userDocId!!)
                .set(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profil diperbarui!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal update", Toast.LENGTH_SHORT).show()
                }
        } else {
            firestore.collection("users").add(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profil disimpan!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal simpan", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun deleteUser() {
        if (userDocId != null) {
            firestore.collection("users").document(userDocId!!)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Profil dihapus!", Toast.LENGTH_SHORT).show()
                    finish() // keluar dari activity setelah hapus
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal hapus profil", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Tidak ada data untuk dihapus", Toast.LENGTH_SHORT).show()
        }
    }
}
