package com.example.meped.presentation.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.meped.data.local.entity.FavoriteEntity
import com.example.meped.presentation.MepedViewModelFactory

@Composable
fun Home(
    navController: NavHostController,
    factory: MepedViewModelFactory
) {
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val showOnboarding by viewModel.showOnboarding.collectAsStateWithLifecycle()

    val favoriteTeams by viewModel.favoriteTeams.collectAsStateWithLifecycle()
    val favoritePlayers by viewModel.favoritePlayers.collectAsStateWithLifecycle()
    val favoriteTournaments by viewModel.favoriteTournaments.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF2F7FF))
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            HeaderWelcome()
            Spacer(modifier = Modifier.height(24.dp))
            JadwalSection()
            Spacer(modifier = Modifier.height(24.dp))
            FavoriteSection(title = "Tim Favorit Kamu", icon = "🦁", items = favoriteTeams)
            Spacer(modifier = Modifier.height(24.dp))
            FavoriteSection(title = "Player Favorit Kamu", icon = "🧑‍💻", items = favoritePlayers)
            Spacer(modifier = Modifier.height(24.dp))
            FavoriteSection(title = "Turnamen Favorit Kamu", icon = "🏆", items = favoriteTournaments)
            Spacer(modifier = Modifier.height(24.dp))
            HasilPertandinganSection()
            Spacer(modifier = Modifier.height(24.dp))
        }
        if (showOnboarding) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable { viewModel.onOnboardingDismissed() }, // Bisa ditutup dengan klik di mana saja
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Selamat Datang di MEPED!", style = MaterialTheme.typography.headlineMedium, color = Color.White, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Di halaman ini, kamu bisa melihat ringkasan dari semua item favoritmu.", style = MaterialTheme.typography.bodyLarge, color = Color.White, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Gunakan tab di bawah untuk menjelajahi Jadwal, Explore, dan Profil!", style = MaterialTheme.typography.bodyLarge, color = Color.White, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(onClick = { viewModel.onOnboardingDismissed() }) {
                        Text("Mengerti!")
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderWelcome() {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp, color = Color(0xFF0016C3))) { append("MP ") }
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color.Black)) { append("Selamat Datang di\n") }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0016C3))) { append("MLBB Esports Pedia") }
        }
    )
}

@Composable
private fun JadwalSection() {
    Text("Jadwal Pertandingan", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0016C3))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("MPL ID SEASON 20", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text("20.30 WITA - LIVE", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Alter Ego Esports", color = Color.White)
                Text("VS", color = Color.Red, fontWeight = FontWeight.Bold)
                Text("Dewa United Esports", color = Color.White)
            }
        }
    }
}

@Composable
private fun FavoriteSection(title: String, icon: String, items: List<FavoriteEntity>) {
    if (items.isNotEmpty()) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("Karena kamu menyukai: $icon", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items) { favoriteItem ->
                Card(shape = RoundedCornerShape(16.dp)) {
                    Box(modifier = Modifier.size(width = 150.dp, height = 80.dp), contentAlignment = Alignment.Center) {
                        Text(favoriteItem.itemName, textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HasilPertandinganSection() {
    Text("Hasil Pertandingan Terbaru", fontWeight = FontWeight.Bold, fontSize = 18.sp)
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(3) {
            Card(
                modifier = Modifier.width(160.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0016C3))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Regular Season\nWeek 9 Day 1", color = Color.White, fontSize = 14.sp, lineHeight = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("RRQ 2 - 0 DEWA", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("MPL ID Season 20", color = Color.LightGray, fontSize = 12.sp)
                }
            }
        }
    }
}