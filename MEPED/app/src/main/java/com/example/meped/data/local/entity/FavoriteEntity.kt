package com.example.meped.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val itemId: Int,
    val itemType: String,
    val itemName: String
)