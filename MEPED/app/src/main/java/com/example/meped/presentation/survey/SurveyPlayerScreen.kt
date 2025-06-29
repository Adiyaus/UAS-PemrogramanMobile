package com.example.meped.presentation.survey

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meped.presentation.*

@Composable
fun SurveyPlayerScreen(
    factory: MepedViewModelFactory,
    sharedViewModel: SharedViewModel,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val viewModel: SurveyViewModel = viewModel(factory = factory)
    val apiState by sharedViewModel.playersState.collectAsState()
    val selectedIds by viewModel.selectedIds.collectAsState()

    SurveyContent(
        title = "Pilih Pemain Favoritmu",
        apiState = apiState,
        selectedIds = selectedIds,
        onItemSelected = viewModel::onItemSelected,
        onNextClick = {
            viewModel.saveFavorites(ApiCategory.PLAYERS, apiState.items)
            onNextClick()
        },
        onSkipClick = onSkipClick,
        onRetry = { sharedViewModel.fetchApiData(ApiCategory.PLAYERS) }
    )
}