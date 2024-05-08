package io.github.justincodinguk.core.database.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String,
    val likeCount: Int,
    val ownerId: String,
    val ownerName: String,
    val ownerProfile: String,
    val photos: String,
    val isFavorite: Boolean,
    val isSelfCreated: Boolean
)