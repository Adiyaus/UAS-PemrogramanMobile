package com.example.meped.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    navController: NavController,
    teamId: String
) {
    LaunchedEffect(key1 = teamId) {
        println("Fetching detail for teamId: $teamId")
    }

    // Tampilan UI untuk halaman detail
    Text(text = "Detail Screen for Team ID: $teamId")

    val team by viewModel.teamDetails.collectAsState()
    val description by viewModel.description.collectAsState()

    var notes by remember(team) { mutableStateOf(team?.userNotes ?: "") }
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            team?.let { currentTeam ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentTeam.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    // Tombol Hapus
                    IconButton(onClick = {
                        viewModel.deleteFavorite()
                        Toast.makeText(context, "${currentTeam.name} dihapus dari favorit", Toast.LENGTH_SHORT).show()
                        navController.popBackStack() // Kembali ke halaman sebelumnya
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus Favorit", tint = Color.Red)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Deskripsi", style = MaterialTheme.typography.titleMedium)
                Text(text = description, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(24.dp))

                Text("Catatan Pribadi:", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Tulis catatanmu di sini...") },
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.updateNotes(notes)
                        Toast.makeText(context, "Catatan disimpan!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Simpan Catatan")
                }

            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}