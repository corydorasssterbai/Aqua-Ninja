package com.kania.aquaninjanew

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ChangeProfileImageActivity : AppCompatActivity() {

    private lateinit var etNewImageUrl: EditText
    private lateinit var ivPreviewImage: ImageView
    private lateinit var btnApplyImage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile_image)

        etNewImageUrl = findViewById(R.id.etNewImageUrl)
        ivPreviewImage = findViewById(R.id.ivPreviewImage)
        btnApplyImage = findViewById(R.id.btnApplyImage)

        // Saat user mengetik URL dan tekan Enter, tampilkan preview
        etNewImageUrl.setOnEditorActionListener { _, _, _ ->
            val url = etNewImageUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .into(ivPreviewImage)
            }
            false
        }

        btnApplyImage.setOnClickListener {
            val imageUrl = etNewImageUrl.text.toString().trim()
            if (imageUrl.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("image_url", imageUrl)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "URL tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
