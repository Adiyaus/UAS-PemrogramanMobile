package com.example.meped.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meped.data.local.OnboardingManager
import com.example.meped.data.local.entity.FavoriteEntity
import com.example.meped.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val repository: TeamRepository,
    private val onboardingManager: OnboardingManager // Terima manajer baru
) : ViewModel() {

    // State untuk mengontrol visibilitas tutorial
    private val _showOnboarding = MutableStateFlow(false)
    val showOnboarding = _showOnboarding.asStateFlow()

    val favoriteTeams: StateFlow<List<FavoriteEntity>> = repository.getFavoriteTeams()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoritePlayers: StateFlow<List<FavoriteEntity>> = repository.getFavoritePlayers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteTournaments: StateFlow<List<FavoriteEntity>> = repository.getFavoriteTournaments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        checkIfOnboardingNeeded()
    }

    private fun checkIfOnboardingNeeded() {
        if (!onboardingManager.hasSeenHomeOnboarding()) {
            _showOnboarding.value = true
        }
    }

    fun onOnboardingDismissed() {
        _showOnboarding.value = false
        onboardingManager.setHomeOnboardingSeen()
    }
}