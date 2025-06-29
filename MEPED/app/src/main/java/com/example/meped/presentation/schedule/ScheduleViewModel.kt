package com.example.meped.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meped.data.local.entity.JadwalEntity
import com.example.meped.data.local.entity.KlasemenEntity
import com.example.meped.domain.repository.TeamRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ScheduleViewModel(repository: TeamRepository) : ViewModel() {

    val standings: StateFlow<List<KlasemenEntity>> = repository
        .getStandings("MPL ID S13")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val schedules: StateFlow<List<JadwalEntity>> = repository
        .getSchedules("MPL ID S13")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}