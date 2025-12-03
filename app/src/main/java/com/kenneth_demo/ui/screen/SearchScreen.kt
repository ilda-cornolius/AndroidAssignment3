package com.kenneth_demo.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kenneth_demo.WeatherApplication
import com.kenneth_demo.data.model.WeatherResponse
import com.kenneth_demo.ui.viewmodel.WeatherUiState
import com.kenneth_demo.ui.viewmodel.WeatherViewModel
import com.kenneth_demo.ui.viewmodel.WeatherViewModelFactory

/**
 * Search screen for searching weather by city name.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(
            (androidx.compose.ui.platform.LocalContext.current.applicationContext as WeatherApplication).weatherRepository
        )
    )
) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter city name") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            singleLine = true,
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Text("✕")
                    }
                }
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search button
        Button(
            onClick = {
                if (searchQuery.isNotBlank()) {
                    viewModel.fetchWeather(searchQuery.trim())
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = searchQuery.isNotBlank()
        ) {
            Text("Search Weather")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search results
        when (val currentState = uiState) {
            is WeatherUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is WeatherUiState.Success -> {
                SearchResultCard(
                    weather = currentState.weather,
                    onNavigateToDetail = onNavigateToDetail
                )
            }
            
            is WeatherUiState.Error -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = currentState.message,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

/**
 * Search result card component.
 */
@Composable
fun SearchResultCard(
    weather: WeatherResponse,
    onNavigateToDetail: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetail(weather.name ?: "") },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = weather.name ?: "Unknown",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            weather.system?.country?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                weather.main?.temperature?.let {
                    Text(
                        text = "${it.toInt()}°C",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                weather.weather?.firstOrNull()?.description?.let { description ->
                    Text(
                        text = description.replaceFirstChar { it.uppercaseChar() },
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Tap to view details →",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

