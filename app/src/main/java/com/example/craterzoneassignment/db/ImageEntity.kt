package com.example.craterzoneassignment.db

import androidx.room.Entity

@Entity(tableName = "Image")
data class ImageEntity(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
)