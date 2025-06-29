package com.example.meped.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meped.data.local.OnboardingManager
import com.example.meped.domain.repository.TeamRepository
import com.example.meped.presentation.explore.ExploreViewModel
import com.example.meped.presentation.home.HomeViewModel
import com.example.meped.presentation.profile.ProfileViewModel
import com.example.meped.presentation.schedule.ScheduleViewModel
import com.example.meped.presentation.survey.SurveyViewModel

@Suppress("UNCHECKED_CAST")
class MepedViewModelFactory(
    private val repository: TeamRepository,
    private val onboardingManager: OnboardingManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SurveyViewModel::class.java) -> {
                SurveyViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository, onboardingManager) as T
            }
            modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> {
                ScheduleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ExploreViewModel::class.java) -> {
                ExploreViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class provided to MepedViewModelFactory: ${modelClass.name}")
        }
    }
}