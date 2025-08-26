package com.example.instagramclone.data.model

data class Post(
    val postId: String = "",
    val userId: String = "",
    val imageUrl: String = "",
    val caption: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
