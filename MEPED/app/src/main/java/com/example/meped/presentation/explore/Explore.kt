package com.example.meped.presentation.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meped.presentation.MepedViewModelFactory
import com.example.meped.presentation.SharedViewModel

@Composable
fun Explore(
    factory: MepedViewModelFactory,
    sharedViewModel: SharedViewModel,
    onNavigateToDetail: () -> Unit
) {
    val viewModel: ExploreViewModel = viewModel(factory = factory)

    val teams by viewModel.filteredTeamsState
    val error by viewModel.errorState
    val isLoading by viewModel.isLoading
    val selectedFilter by viewModel.selectedFilter

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Daftar Tim eSports MLBB", fontWeight = FontWeight.Bold, fontSize = 24.dp.value.sp)
        Spacer(modifier = Modifier.height(16.dp))

        FilterChips(
            selectedFilter = selectedFilter,
            onFilterSelected = { filter -> viewModel.applyFilter(filter) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                val currentError = error
                if (currentError != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = currentError)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.fetchAllTeams() }) {
                            Text("Coba Lagi")
                        }
                    }
                } else if (teams.isEmpty()) {
                    Text("Tidak ada tim untuk filter ini.")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(teams) { team ->
                            TeamCard(
                                teamName = team.title,
                                onClick = {
                                    viewModel.onTeamClicked(team, sharedViewModel)
                                    onNavigateToDetail()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(selectedFilter: String, onFilterSelected: (String) -> Unit) {
    val filters = listOf(ManualTeamLists.ALL_TEAMS, ManualTeamLists.MPL_ID, ManualTeamLists.MPL_PH)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                leadingIcon = if (selectedFilter == filter) {
                    { Icon(imageVector = Icons.Filled.Done, contentDescription = "Done") }
                } else {
                    null
                }
            )
        }
    }
}

@Composable
fun TeamCard(teamName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = teamName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}