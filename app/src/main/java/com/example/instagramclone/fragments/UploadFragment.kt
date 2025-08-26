package com.example.instagramclone.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.instagramclone.R
import com.example.instagramclone.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UploadFragment : Fragment() {

    private lateinit var previewImage: ImageView
    private lateinit var captionInput: EditText
    private lateinit var selectImageBtn: Button
    private lateinit var uploadBtn: Button
    private var selectedImageUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 100

    private val storage = FirebaseStorage.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload, container, false)

        previewImage = view.findViewById(R.id.previewImage)
        captionInput = view.findViewById(R.id.captionInput)
        selectImageBtn = view.findViewById(R.id.selectImageBtn)
        uploadBtn = view.findViewById(R.id.uploadBtn)

        selectImageBtn.setOnClickListener { openGallery() }
        uploadBtn.setOnClickListener { uploadPost() }

        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            Glide.with(this).load(selectedImageUri).into(previewImage)
        }
    }

    private fun uploadPost() {
        val caption = captionInput.text.toString().trim()
        val userId = auth.currentUser?.uid ?: return
        val imageUri = selectedImageUri ?: return

        val fileName = UUID.randomUUID().toString()
        val storageRef = storage.reference.child("posts/$fileName.jpg")

        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val post = Post(
                        imageUrl = uri.toString(),
                        caption = caption,
                        userId = userId,
                        timestamp = System.currentTimeMillis()
                    )

                    db.collection("posts")
                        .add(post)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Post uploaded!", Toast.LENGTH_SHORT).show()
                            captionInput.text.clear()
                            previewImage.setImageResource(0)
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Failed to save post!", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Upload failed!", Toast.LENGTH_SHORT).show()
            }
    }
}
