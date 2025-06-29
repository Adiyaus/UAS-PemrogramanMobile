package com.example.meped.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.meped.data.local.entity.FavoriteEntity
import com.example.meped.presentation.MepedViewModelFactory
import com.example.meped.presentation.navigation.Screen

@Composable
fun Profil(
    navController: NavHostController,
    factory: MepedViewModelFactory
) {
    val viewModel: ProfileViewModel = viewModel(factory = factory)
    val favoriteTeams by viewModel.favoriteTeams.collectAsState()
    val favoritePlayers by viewModel.favoritePlayers.collectAsState()
    val favoriteTournaments by viewModel.favoriteTournaments.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text("Profil & Favorit", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { navController.navigate(Screen.Survey.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ubah Pilihan Favorit")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Tampilkan daftar Tim Favorit
        item {
            Text("Tim Favorit", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (favoriteTeams.isEmpty()) {
            item { Text("Belum ada tim favorit yang dipilih.", modifier = Modifier.padding(bottom = 16.dp)) }
        } else {
            items(favoriteTeams) { favorite ->
                FavoriteItemRow(item = favorite, onDelete = { viewModel.deleteFavorite(favorite) })
            }
        }

        // Tampilkan daftar Player Favorit
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Player Favorit", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (favoritePlayers.isEmpty()) {
            item { Text("Belum ada pemain favorit yang dipilih.", modifier = Modifier.padding(bottom = 16.dp)) }
        } else {
            items(favoritePlayers) { favorite ->
                FavoriteItemRow(item = favorite, onDelete = { viewModel.deleteFavorite(favorite) })
            }
        }

        // Tampilkan daftar Turnamen Favorit
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Turnamen Favorit", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (favoriteTournaments.isEmpty()) {
            item { Text("Belum ada turnamen favorit yang dipilih.", modifier = Modifier.padding(bottom = 16.dp)) }
        } else {
            items(favoriteTournaments) { favorite ->
                FavoriteItemRow(item = favorite, onDelete = { viewModel.deleteFavorite(favorite) })
            }
        }


        // Tombol Logout
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Logout")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    throw RuntimeException("Test Crash dari Halaman Profil by MEPED")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Test Crashlytics (Tombol Darurat)")
            }
        }
    }
}

@Composable
fun FavoriteItemRow(item: FavoriteEntity, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.itemName, modifier = Modifier.weight(1f), fontWeight = FontWeight.SemiBold)
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus ${item.itemName}", tint = Color.Gray)
            }
        }
    }
}