package com.example.meped.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedules")
data class JadwalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val week: String,
    val date: String,
    val time: String,
    val teamA_name: String,
    val teamB_name: String,
    val status: String,
    val turnamen: String
)