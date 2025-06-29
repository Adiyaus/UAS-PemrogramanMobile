package com.example.meped.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meped.data.local.entity.TeamEntity
import com.example.meped.domain.repository.TeamRepository
import com.example.meped.presentation.SharedViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: TeamRepository,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _description = MutableStateFlow("Memuat deskripsi...")
    val description: StateFlow<String> = _description

    val teamDetails: StateFlow<TeamEntity?> = sharedViewModel.selectedTeamId
        .filterNotNull()
        .flatMapLatest { id ->
            repository.getTeamDetails(id)
        }
        .onEach { teamEntity ->
            teamEntity?.let { fetchDescription(it.name) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private fun fetchDescription(teamName: String) {
        viewModelScope.launch {
            _description.value = repository.getTeamDescriptionFromApi(teamName)
        }
    }

    fun updateNotes(notes: String) {
        teamDetails.value?.let { team ->
            viewModelScope.launch {
                repository.updateTeamNotes(team.pageId, notes)
            }
        }
    }
    fun deleteFavorite() {
        teamDetails.value?.let { team ->
            viewModelScope.launch {
                repository.deleteFavorite(team.pageId)
            }
        }
    }
}