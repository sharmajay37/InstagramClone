package com.example.instagramclone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramclone.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UploadPostActivity : AppCompatActivity() {

    private lateinit var pickImageBtn: Button
    private lateinit var uploadBtn: Button
    private lateinit var selectedImage: ImageView
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)

        pickImageBtn = findViewById(R.id.pickImageBtn)
        uploadBtn = findViewById(R.id.uploadBtn)
        selectedImage = findViewById(R.id.selectedImage)

        // Pick Image
        pickImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        // Upload Image
        uploadBtn.setOnClickListener {
            if (imageUri != null) {
                uploadImageToFirebase(imageUri!!)
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            selectedImage.setImageURI(imageUri)
        }
    }

    private fun uploadImageToFirebase(uri: Uri) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val storageRef = FirebaseStorage.getInstance().getReference("posts/$fileName")

        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    savePostToFirestore(downloadUrl.toString(), uid)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Uploading failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun savePostToFirestore(imageUrl: String, userId: String) {
        val post = hashMapOf(
            "imageUrl" to imageUrl,
            "userId" to userId
        )

        FirebaseFirestore.getInstance().collection("posts")
            .add(post)
            .addOnSuccessListener {
                Toast.makeText(this, "Post uploaded!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
