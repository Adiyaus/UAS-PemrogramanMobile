package com.example.meped.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "standings")
data class KlasemenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val rank: Int,
    val teamName: String,
    val wins: Int,
    val losses: Int,
    val points: Int,
    val turnamen: String
)