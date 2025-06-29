package com.example.meped.presentation.explore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meped.data.remote.dto.TeamDto
import com.example.meped.domain.repository.TeamRepository
import com.example.meped.presentation.SharedViewModel
import kotlinx.coroutines.launch
import java.io.IOException

object ManualTeamLists {
    const val ALL_TEAMS = "SEMUA"
    const val MPL_ID = "MPL ID"
    const val MPL_PH = "MPL PH"
    val mplIdTeams = listOf("ONIC", "Bigetron Alpha", "EVOS Glory", "Geek Fam", "RRQ Hoshi", "AURA", "Alter Ego", "Rebellion", "Dewa United")
    val mplPhTeams = listOf("Falcons AP Bren", "Blacklist International", "ECHO", "RSG Philippines", "Smart Omega", "TNC Pro Team", "Minana EVOS", "ONIC Philippines")
}

class ExploreViewModel(private val repository: TeamRepository) : ViewModel() {

    private val _allTeamsFromApi = mutableStateOf<List<TeamDto>>(emptyList())
    private val _filteredTeamsState = mutableStateOf<List<TeamDto>>(emptyList())
    val filteredTeamsState: State<List<TeamDto>> = _filteredTeamsState

    private val _errorState = mutableStateOf<String?>(null)
    val errorState: State<String?> = _errorState

    private val _selectedFilter = mutableStateOf(ManualTeamLists.ALL_TEAMS)
    val selectedFilter: State<String> = _selectedFilter

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        fetchAllTeams()
    }

    fun fetchAllTeams() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorState.value = null
            try {
                val allTeams = repository.getApiCategory("Category:Teams")
                _allTeamsFromApi.value = allTeams
                applyFilter(_selectedFilter.value)
            } catch (e: IOException) {
                _errorState.value = "Koneksi Error. Cek internet lo, brok."
            } catch (e: Exception) {
                _errorState.value = "Gagal memuat. Server mungkin sibuk."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun applyFilter(filter: String) {
        _selectedFilter.value = filter
        _filteredTeamsState.value = when (filter) {
            ManualTeamLists.MPL_ID -> _allTeamsFromApi.value.filter { apiTeam -> ManualTeamLists.mplIdTeams.any { idTeam -> apiTeam.title.contains(idTeam, ignoreCase = true) } }
            ManualTeamLists.MPL_PH -> _allTeamsFromApi.value.filter { apiTeam -> ManualTeamLists.mplPhTeams.any { phTeam -> apiTeam.title.contains(phTeam, ignoreCase = true) } }
            else -> _allTeamsFromApi.value
        }
    }

    fun onTeamClicked(teamDto: TeamDto, sharedViewModel: SharedViewModel) {
        sharedViewModel.selectTeamForDetail(teamDto.pageId)
        viewModelScope.launch {
            repository.saveTeamToDb(teamDto)
        }
    }
}