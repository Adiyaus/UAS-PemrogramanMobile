package com.example.meped.presentation.survey

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meped.presentation.*
import com.google.firebase.analytics.ktx.analytics // Import baru
import com.google.firebase.ktx.Firebase

@Composable
fun SurveyTournamentScreen(
    factory: MepedViewModelFactory,
    sharedViewModel: SharedViewModel,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val viewModel: SurveyViewModel = viewModel(factory = factory)
    val apiState by sharedViewModel.tournamentsState.collectAsState()
    val selectedIds by viewModel.selectedIds.collectAsState()

    SurveyContent(
        title = "Pilih Turnamen Favoritmu",
        apiState = apiState,
        selectedIds = selectedIds,
        onItemSelected = viewModel::onItemSelected,
        onNextClick = {
            viewModel.saveFavorites(ApiCategory.TOURNAMENTS, apiState.items)
            // --- KIRIM LAPORAN KE FIREBASE ANALYTICS ---
            val surveyParams = Bundle().apply {
                putString("survey_result", "completed_with_selection")
                putInt("tournament_selection_count", selectedIds.size)
            }
            Firebase.analytics.logEvent("survey_finished", surveyParams)
            // --- SELESAI MENGIRIM LAPORAN ---
            onNextClick()
        },
        onSkipClick = {
            // --- KIRIM LAPORAN JUGA JIKA USER SKIP ---
            val surveyParams = Bundle().apply {
                putString("survey_result", "skipped")
            }
            Firebase.analytics.logEvent("survey_finished", surveyParams)
            // --- SELESAI MENGIRIM LAPORAN ---
            onSkipClick()},
        onRetry = { sharedViewModel.fetchApiData(ApiCategory.TOURNAMENTS) }
    )
}