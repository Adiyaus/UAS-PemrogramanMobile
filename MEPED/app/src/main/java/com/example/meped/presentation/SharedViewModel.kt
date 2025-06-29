package com.example.meped.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.meped.data.remote.dto.TeamDto
import com.example.meped.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

enum class ApiCategory(val key: String) {
    TEAMS("Category:Teams"),
    PLAYERS("Category:Players"),
    TOURNAMENTS("Category:Tournaments")
}

data class ApiDataState(
    val isLoading: Boolean = false,
    val items: List<TeamDto> = emptyList(),
    val error: String? = null,
)

class SharedViewModel(private val repository: TeamRepository) : ViewModel() {
    private val _teamsState = MutableStateFlow(ApiDataState(isLoading = true))
    val teamsState = _teamsState.asStateFlow()

    private val _playersState = MutableStateFlow(ApiDataState(isLoading = true))
    val playersState = _playersState.asStateFlow()

    private val _tournamentsState = MutableStateFlow(ApiDataState(isLoading = true))
    val tournamentsState = _tournamentsState.asStateFlow()

    private val _selectedTeamId = MutableStateFlow<Int?>(null)
    val selectedTeamId = _selectedTeamId.asStateFlow()

    init {
        fetchApiData(ApiCategory.TEAMS)
        fetchApiData(ApiCategory.PLAYERS)
        fetchApiData(ApiCategory.TOURNAMENTS)
    }

    fun selectTeamForDetail(teamId: Int) {
        _selectedTeamId.value = teamId
    }

    fun fetchApiData(category: ApiCategory) {
        val stateFlow = getStateFlowForCategory(category)
        if (stateFlow.value.isLoading && stateFlow.value.items.isNotEmpty()) return

        viewModelScope.launch {
            stateFlow.update { it.copy(isLoading = true, error = null) }
            try {
                val newItems = repository.getApiCategory(category.key)
                stateFlow.update { ApiDataState(isLoading = false, items = newItems) }
            } catch (e: Exception) {
                stateFlow.update { it.copy(isLoading = false, error = "Gagal Memuat. Coba lagi nanti.") }
            }
        }
    }

    private fun getStateFlowForCategory(category: ApiCategory): MutableStateFlow<ApiDataState> {
        return when(category) {
            ApiCategory.TEAMS -> _teamsState
            ApiCategory.PLAYERS -> _playersState
            ApiCategory.TOURNAMENTS -> _tournamentsState
        }
    }

    companion object {
        fun provideFactory(repository: TeamRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SharedViewModel(repository) as T
                }
            }
        }
    }
}