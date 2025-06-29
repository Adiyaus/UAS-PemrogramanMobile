package com.example.meped.data.remote.dto

import com.google.gson.annotations.SerializedName

// Ini adalah struktur JSON yang dikirim oleh Liquipedia
data class LiquipediaResponseDto(
    val query: QueryDto
)

data class QueryDto(
    @SerializedName("categorymembers")
    val categoryMembers: List<TeamDto>
)

// Ini adalah "cetakan" untuk setiap tim di dalam daftar
data class TeamDto(
    @SerializedName("pageid")
    val pageId: Int,
    val title: String
)