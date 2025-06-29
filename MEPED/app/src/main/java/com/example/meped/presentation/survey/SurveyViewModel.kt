package com.example.meped.presentation.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meped.data.local.entity.FavoriteEntity
import com.example.meped.data.remote.dto.TeamDto
import com.example.meped.domain.repository.TeamRepository
import com.example.meped.presentation.ApiCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SurveyViewModel(private val repository: TeamRepository) : ViewModel() {
    private val _selectedIds = MutableStateFlow<Set<Int>>(emptySet())
    val selectedIds = _selectedIds.asStateFlow()

    fun onItemSelected(itemId: Int) {
        val currentSelection = _selectedIds.value.toMutableSet()
        if (currentSelection.contains(itemId)) currentSelection.remove(itemId) else currentSelection.add(itemId)
        _selectedIds.value = currentSelection
    }

    fun saveFavorites(type: ApiCategory, allItems: List<TeamDto>) {
        viewModelScope.launch {
            val itemTypeString = when (type) {
                ApiCategory.TEAMS -> "TEAM"
                ApiCategory.PLAYERS -> "PLAYER"
                ApiCategory.TOURNAMENTS -> "TOURNAMENT"
            }
            val favoritesToSave = allItems
                .filter { it.pageId in _selectedIds.value }
                .map { FavoriteEntity(itemId = it.pageId, itemType = itemTypeString, itemName = it.title) }

            if (favoritesToSave.isNotEmpty()) {
                repository.saveFavorites(favoritesToSave)
            }
        }
    }
}