package com.example.meped.domain.repository

import com.example.meped.data.local.entity.FavoriteEntity
import com.example.meped.data.local.entity.JadwalEntity
import com.example.meped.data.local.entity.KlasemenEntity
import com.example.meped.data.local.entity.TeamEntity
import com.example.meped.data.remote.dto.TeamDto
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    suspend fun getApiCategory(category: String): List<TeamDto>
    suspend fun getTeamDescriptionFromApi(pageTitle: String): String
    fun getTeamDetails(id: Int): Flow<TeamEntity?>
    suspend fun saveTeamToDb(team: TeamDto)
    suspend fun updateTeamNotes(id: Int, notes: String)
    fun getStandings(turnamen: String): Flow<List<KlasemenEntity>>
    fun getSchedules(turnamen: String): Flow<List<JadwalEntity>>
    suspend fun saveFavorites(favorites: List<FavoriteEntity>)
    fun getFavoriteTeams(): Flow<List<FavoriteEntity>>
    fun getFavoritePlayers(): Flow<List<FavoriteEntity>>
    fun getFavoriteTournaments(): Flow<List<FavoriteEntity>>
    suspend fun deleteFavorite(itemId: Int)
}