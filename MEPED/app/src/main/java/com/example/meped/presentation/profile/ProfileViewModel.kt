package com.example.meped.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meped.data.local.entity.FavoriteEntity
import com.example.meped.domain.repository.TeamRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: TeamRepository) : ViewModel() {

    init {
        Log.d("FAVORITE_DEBUG", "--- [ProfileViewModel] ViewModel Dibuat. Mulai membaca data favorit...")
    }

    val favoriteTeams: StateFlow<List<FavoriteEntity>> = repository.getFavoriteTeams()
        .onEach { teams ->
            Log.d("FAVORITE_DEBUG", "--- [ProfileViewModel] Dapat update data TIM Favorit. Jumlah: ${teams.size}")
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoritePlayers: StateFlow<List<FavoriteEntity>> = repository.getFavoritePlayers()
        .onEach { players ->
            Log.d("FAVORITE_DEBUG", "--- [ProfileViewModel] Dapat update data PLAYER Favorit. Jumlah: ${players.size}")
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteTournaments: StateFlow<List<FavoriteEntity>> = repository.getFavoriteTournaments()
        .onEach { tournaments ->
            Log.d("FAVORITE_DEBUG", "--- [ProfileViewModel] Dapat update data TURNAMEN Favorit. Jumlah: ${tournaments.size}")
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            Log.d("FAVORITE_DEBUG", "--- [ProfileViewModel] Menghapus favorit: ${favorite.itemName}")
            repository.deleteFavorite(favorite.itemId)
        }
    }
}