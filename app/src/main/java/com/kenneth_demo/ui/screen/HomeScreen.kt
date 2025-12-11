package com.kenneth_demo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kenneth_demo.WeatherApplication
import com.kenneth_demo.data.model.WeatherResponse
import com.kenneth_demo.ui.viewmodel.WeatherUiState
import com.kenneth_demo.ui.viewmodel.WeatherViewModel
import com.kenneth_demo.ui.viewmodel.WeatherViewModelFactory

/**
 * Home screen displaying weather information and providing navigation options.
 */
 //UI design of the home screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    //Creating listeners when the user searches a city, navigates to the favorites screen
    //or goes to the detail screen
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(
            (androidx.compose.ui.platform.LocalContext.current.applicationContext as WeatherApplication).weatherRepository
        )
    )
) {
    //checks the state of the weather data
    val uiState by viewModel.uiState.collectAsState()
    //checks if the city displayed is favorited
    val isFavorite by viewModel.isFavorite.collectAsState()
    
    // Sets London as the default city to show when the application launches
    LaunchedEffect(Unit) {
        viewModel.fetchWeather("London") // Default city: London
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //button to navigate to the search screen
            Button(
                onClick = onNavigateToSearch,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search")
            }
            
            //button to navgiate to favorites
            Button(
                onClick = onNavigateToFavorites,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Favorites")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        //the weather content function of the displayed card
        when (val currentState = uiState) {
            is WeatherUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(32.dp))
                Text("Loading weather data...", modifier = Modifier.padding(top = 16.dp))
            }
            //if the api call is successful it will store the details in the below variables
            is WeatherUiState.Success -> {
                WeatherContent(
                    weather = currentState.weather,
                    isFavorite = isFavorite,
                    onToggleFavorite = { viewModel.toggleFavorite(currentState.weather) },
                    onRefresh = { viewModel.fetchWeather(currentState.weather.name ?: "London", true) },
                    onNavigateToDetail = onNavigateToDetail
                )
            }
            
            //if there is an error in the weather api call an error message is displays 
            is WeatherUiState.Error -> {
                ErrorContent(
                    message = currentState.message,
                    onRetry = { viewModel.fetchWeather("London") }
                )
            }
        }
    }
}

//ui design for the weather content cards on the home screen
@Composable
fun WeatherContent(
    weather: WeatherResponse,
    isFavorite: Boolean,
    //sets listeners for when the favorite button or detail card is pressed.
    //sets a listener if the application refreshes
    onToggleFavorite: () -> Unit,
    onRefresh: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // displays the city name and favorite button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = weather.name ?: "Unknown",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            
            Row {
                IconButton(onClick = onRefresh) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
                
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Displays the Country code
        weather.system?.country?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Displays the temperature of the city
        weather.main?.temperature?.let {
            Text(
                text = "${it.toInt()}°C",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Displays the weather description of the city 
        weather.weather?.firstOrNull()?.description?.let { description ->
            Text(
                text = description.replaceFirstChar { it.uppercaseChar() },
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // displays the weather description of the city/card
        //this includes details like humidity and air pressure
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                WeatherDetailRow("Feels like", "${weather.main?.feelsLike?.toInt() ?: "N/A"}°C")
                WeatherDetailRow("Min", "${weather.main?.tempMin?.toInt() ?: "N/A"}°C")
                WeatherDetailRow("Max", "${weather.main?.tempMax?.toInt() ?: "N/A"}°C")
                WeatherDetailRow("Humidity", "${weather.main?.humidity ?: "N/A"}%")
                WeatherDetailRow("Pressure", "${weather.main?.pressure ?: "N/A"} hPa")
                weather.wind?.speed?.let {
                    WeatherDetailRow("Wind Speed", "${it.toInt()} m/s")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // View details button
        Button(
            onClick = { onNavigateToDetail(weather.name ?: "") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Full Details")
        }
    }
}


//Formatting for some of the detail data in the weather cards
@Composable
fun WeatherDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

//Displays a error message on the screen if the API call fails
@Composable
fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = message,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

