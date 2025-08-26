package com.example.instagramclone.data.repository

import com.example.instagramclone.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class PostRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    suspend fun uploadPost(userId: String, imageBytes: ByteArray, caption: String): Boolean {
        return try {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val imageRef = storage.child("posts/$fileName")

            // Upload to Storage
            imageRef.putBytes(imageBytes).await()
            val downloadUrl = imageRef.downloadUrl.await().toString()

            // Create Post
            val post = Post(
                postId = UUID.randomUUID().toString(),
                userId = userId,
                imageUrl = downloadUrl,
                caption = caption
            )

            firestore.collection("posts").document(post.postId).set(post).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getAllPosts(): List<Post> {
        return try {
            val snapshot = firestore.collection("posts")
                .orderBy("timestamp")
                .get()
                .await()
            snapshot.toObjects(Post::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getUserPosts(userId: String): List<Post> {
        return try {
            val snapshot = firestore.collection("posts")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            snapshot.toObjects(Post::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
