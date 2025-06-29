package com.example.meped.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    val pageId: Int,
    val name: String,
    val userNotes: String
)