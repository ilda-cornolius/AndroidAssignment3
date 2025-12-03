package com.kenneth_demo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kenneth_demo.WeatherApplication
import com.kenneth_demo.ui.viewmodel.WeatherUiState
import com.kenneth_demo.ui.viewmodel.WeatherViewModel
import com.kenneth_demo.ui.viewmodel.WeatherViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Detail screen showing comprehensive weather information for a specific city.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    cityName: String,
    onNavigateBack: () -> Unit,
    viewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(
            (androidx.compose.ui.platform.LocalContext.current.applicationContext as WeatherApplication).weatherRepository
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    
    LaunchedEffect(cityName) {
        viewModel.fetchWeather(cityName)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (uiState is WeatherUiState.Success) {
                        val weather = (uiState as WeatherUiState.Success).weather
                        
                        IconButton(onClick = {
                            viewModel.fetchWeather(cityName, true)
                        }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                        
                        IconButton(onClick = {
                            viewModel.toggleFavorite(weather)
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color.Red else Color.Gray
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val currentState = uiState) {
            is WeatherUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading weather details...")
                    }
                }
            }
            
            is WeatherUiState.Success -> {
                DetailedWeatherContent(
                    weather = currentState.weather,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                )
            }
            
            is WeatherUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    ErrorContent(
                        message = currentState.message,
                        onRetry = { viewModel.fetchWeather(cityName) }
                    )
                }
            }
        }
    }
}

/**
 * Detailed weather content component.
 */
@Composable
fun DetailedWeatherContent(
    weather: com.kenneth_demo.data.model.WeatherResponse,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // City name
        Text(
            text = weather.name ?: "Unknown",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        
        weather.system?.country?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Coordinates
        weather.coordinates?.let { coord ->
            Text(
                text = "Lat: ${String.format("%.2f", coord.latitude)}, Lon: ${String.format("%.2f", coord.longitude)}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Temperature
        weather.main?.temperature?.let {
            Text(
                text = "${it.toInt()}°C",
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Weather description
        weather.weather?.firstOrNull()?.description?.let { description ->
            Text(
                text = description.replaceFirstChar { it.uppercaseChar() },
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Weather details cards
        WeatherDetailsCard(
            title = "Temperature",
            items = listOf(
                "Feels like" to "${weather.main?.feelsLike?.toInt() ?: "N/A"}°C",
                "Min" to "${weather.main?.tempMin?.toInt() ?: "N/A"}°C",
                "Max" to "${weather.main?.tempMax?.toInt() ?: "N/A"}°C"
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        WeatherDetailsCard(
            title = "Atmospheric",
            items = listOf(
                "Pressure" to "${weather.main?.pressure ?: "N/A"} hPa",
                "Humidity" to "${weather.main?.humidity ?: "N/A"}%",
                "Visibility" to "${weather.visibility?.let { it / 1000 } ?: "N/A"} km"
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        WeatherDetailsCard(
            title = "Wind & Clouds",
            items = listOf(
                "Wind Speed" to "${weather.wind?.speed?.toInt() ?: "N/A"} m/s",
                "Wind Direction" to "${weather.wind?.degree ?: "N/A"}°",
                "Clouds" to "${weather.clouds?.all ?: "N/A"}%"
            )
        )
        
        weather.system?.let { sys ->
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Sun",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Sunrise
                    WeatherDetailRow(
                        "Sunrise",
                        sys.sunrise?.let { sunrise ->
                            val sunriseDate = Date(sunrise * 1000)
                            val sunriseFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                            sunriseFormat.format(sunriseDate)
                        } ?: "N/A"
                    )
                    
                    // Sunset
                    WeatherDetailRow(
                        "Sunset",
                        sys.sunset?.let { sunset ->
                            val sunsetDate = Date(sunset * 1000)
                            val sunsetFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                            sunsetFormat.format(sunsetDate)
                        } ?: "N/A"
                    )
                }
            }
        }
        
        weather.dateTime?.let { dt ->
            Spacer(modifier = Modifier.height(16.dp))
            
            val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            val date = Date(dt * 1000)
            
            Text(
                text = "Last updated: ${dateFormat.format(date)}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Weather details card component.
 */
@Composable
fun WeatherDetailsCard(
    title: String,
    items: List<Pair<String, String>>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            items.forEach { (label, value) ->
                WeatherDetailRow(label, value)
            }
        }
    }
}

