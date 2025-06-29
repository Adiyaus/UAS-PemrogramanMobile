package com.example.meped.presentation.survey

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meped.presentation.ApiDataState

@Composable
fun SurveyContent(
    title: String,
    apiState: ApiDataState,
    selectedIds: Set<Int>,
    onItemSelected: (Int) -> Unit,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text("Pilihanmu akan membantu kami menampilkan konten yang relevan.", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            if (apiState.isLoading) {
                CircularProgressIndicator()
            } else if (apiState.error != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = apiState.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRetry) { Text("Coba Lagi") }
                }
            } else {
                LazyColumn {
                    items(apiState.items) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onItemSelected(item.pageId) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedIds.contains(item.pageId),
                                onCheckedChange = { onItemSelected(item.pageId) }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNextClick, modifier = Modifier.fillMaxWidth()) {
            Text("Lanjut")
        }
        TextButton(onClick = onSkipClick) {
            Text("Lewati Saja")
        }
    }
}