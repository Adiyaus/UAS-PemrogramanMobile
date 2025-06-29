package com.example.meped.presentation.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meped.data.local.entity.JadwalEntity
import com.example.meped.data.local.entity.KlasemenEntity
import com.example.meped.presentation.MepedViewModelFactory

@Composable
fun Schedule(factory: MepedViewModelFactory) {
    val viewModel: ScheduleViewModel = viewModel(factory = factory)
    val standings by viewModel.standings.collectAsState()
    val schedules by viewModel.schedules.collectAsState()
    val groupedSchedules = schedules.groupBy { it.week }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F7FF))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Bagian Klasemen ---
        item {
            Text("Klasemen Sementara", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    HeaderKlasemen()
                    if (standings.isEmpty()) {
                        Box(modifier = Modifier.padding(16.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text("Data klasemen tidak tersedia.")
                        }
                    } else {
                        standings.forEachIndexed { index, standing ->
                            RowKlasemen(standing = standing, index = index)
                        }
                    }
                }
            }
        }

        // --- Bagian Jadwal ---
        item {
            Text("Jadwal Pertandingan", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        if (groupedSchedules.isEmpty()) {
            item { Text("Tidak ada jadwal pertandingan.") }
        } else {
            groupedSchedules.forEach { (week, matchesInWeek) ->
                item {
                    Text(
                        text = week,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0016C3)
                    )
                }
                items(matchesInWeek) { match ->
                    MatchCard(match = match)
                }
            }
        }
    }
}

@Composable
private fun HeaderKlasemen() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text("Rank", modifier = Modifier.weight(0.15f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Text("Tim", modifier = Modifier.weight(0.4f), fontWeight = FontWeight.Bold)
        Text("W-L", modifier = Modifier.weight(0.25f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Text("Pts", modifier = Modifier.weight(0.2f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun RowKlasemen(standing: KlasemenEntity, index: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(if (index % 2 == 0) MaterialTheme.colorScheme.surface.copy(alpha = 0.5f) else Color.Transparent)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(standing.rank.toString(), modifier = Modifier.weight(0.15f), textAlign = TextAlign.Center)
        Text(standing.teamName, modifier = Modifier.weight(0.4f), fontWeight = FontWeight.SemiBold)
        Text("${standing.wins}-${standing.losses}", modifier = Modifier.weight(0.25f), textAlign = TextAlign.Center)
        Text(standing.points.toString(), modifier = Modifier.weight(0.2f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MatchCard(match: JadwalEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${match.date} - ${match.time}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = match.teamA_name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                Text(text = match.status, style = MaterialTheme.typography.bodyLarge, color = if (match.status == "VS") Color.Red else Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
                Text(text = match.teamB_name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Start)
            }
        }
    }
}