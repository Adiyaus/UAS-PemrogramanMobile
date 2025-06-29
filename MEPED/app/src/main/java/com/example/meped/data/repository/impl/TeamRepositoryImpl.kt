package com.example.meped.data.repository.impl

import android.util.Log
import com.example.meped.data.DummyDataSource
import com.example.meped.data.local.dao.FavoriteDao
import com.example.meped.data.local.dao.JadwalDao
import com.example.meped.data.local.dao.KlasemenDao
import com.example.meped.data.local.dao.TeamDao
import com.example.meped.data.local.entity.FavoriteEntity
import com.example.meped.data.local.entity.JadwalEntity
import com.example.meped.data.local.entity.KlasemenEntity
import com.example.meped.data.local.entity.TeamEntity
import com.example.meped.data.remote.api.LiquipediaApiService
import com.example.meped.data.remote.dto.TeamDto
import com.example.meped.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import org.jsoup.Jsoup

class TeamRepositoryImpl(
    private val apiService: LiquipediaApiService,
    private val teamDao: TeamDao,
    private val klasemenDao: KlasemenDao,
    private val jadwalDao: JadwalDao,
    private val favoriteDao: FavoriteDao
) : TeamRepository {

    // --- PERBAIKAN UTAMA DI SINI: "KANTONG" UNTUK CACHE ---
    private var teamsCache: List<TeamDto>? = null
    private var playersCache: List<TeamDto>? = null
    private var tournamentsCache: List<TeamDto>? = null

    override suspend fun getApiCategory(category: String): List<TeamDto> {
        Log.d("REPO_DEBUG", "Mencoba mengambil '$category' dari Rencana A (API)...")
        return try {
            // RENCANA A: Coba ambil dari API
            val response = apiService.getCategoryMembers(category = category)
            Log.d("REPO_DEBUG", "BERHASIL! Rencana A dieksekusi untuk '$category'.")
            response.query.categoryMembers
        } catch (e: Exception) {
            Log.e("REPO_DEBUG", "GAGAL! Rencana A gagal untuk '$category'. Menjalankan Rencana B (Dummy Data). Error: ${e.message}")
            when (category) {
                "Category:Teams" -> DummyDataSource.dummyTeams
                "Category:Players" -> DummyDataSource.dummyPlayers
                "Category:Tournaments" -> DummyDataSource.dummyTournaments
                else -> emptyList()
            }
        }
    }

    // --- SISA FUNGSI DI BAWAH INI TIDAK ADA PERUBAHAN ---
    override suspend fun getTeamDescriptionFromApi(pageTitle: String): String {
        return try {
            val response = apiService.getPageContent(pageTitle = pageTitle)
            val jsonString = response.string()
            val jsonObject = JSONObject(jsonString)
            val htmlContent = jsonObject.getJSONObject("parse").getJSONObject("text").getString("*")
            val document = Jsoup.parse(htmlContent)
            document.select("p").first()?.text() ?: "Deskripsi tidak ditemukan."
        } catch (e: Exception) { "Gagal memuat deskripsi." }
    }

    override fun getTeamDetails(id: Int): Flow<TeamEntity?> = teamDao.getTeamById(id)
    override suspend fun saveTeamToDb(team: TeamDto) {
        val existingTeam = teamDao.getTeamByIdOnce(team.pageId)
        if (existingTeam == null) {
            val newTeamEntity = TeamEntity(pageId = team.pageId, name = team.title, userNotes = "")
            teamDao.insertTeam(newTeamEntity)
        }
    }
    override suspend fun updateTeamNotes(id: Int, notes: String) = teamDao.updateNotes(id, notes)
    override fun getStandings(turnamen: String): Flow<List<KlasemenEntity>> = klasemenDao.getStandingsByTournament(turnamen)
    override fun getSchedules(turnamen: String): Flow<List<JadwalEntity>> = jadwalDao.getSchedulesByTournament(turnamen)
    override suspend fun saveFavorites(favorites: List<FavoriteEntity>) = favoriteDao.insertAll(favorites)
    override suspend fun deleteFavorite(itemId: Int) {
        favoriteDao.deleteFavoriteById(itemId)
    }
    override fun getFavoriteTeams(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getFavoritesByType("TEAM")
    }

    override fun getFavoritePlayers(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getFavoritesByType("PLAYER")
    }

    override fun getFavoriteTournaments(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getFavoritesByType("TOURNAMENT")
    }
}