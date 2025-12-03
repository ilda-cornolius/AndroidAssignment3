package com.kenneth_demo.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kenneth_demo.WeatherApplication
import com.kenneth_demo.data.local.entity.FavoriteLocation
import com.kenneth_demo.ui.viewmodel.FavoritesViewModel
import com.kenneth_demo.ui.viewmodel.FavoritesViewModelFactory

/**
 * Favorites screen displaying all favorite locations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(
            (androidx.compose.ui.platform.LocalContext.current.applicationContext as WeatherApplication).weatherRepository
        )
    )
) {
    val favorites by viewModel.favorites.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Locations") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No favorites yet",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Add locations to favorites to see them here",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = favorites,
                    key = { it.id }
                ) { favorite ->
                    FavoriteLocationCard(
                        favorite = favorite,
                        onDelete = { viewModel.removeFavorite(favorite) },
                        onClick = { onNavigateToDetail(favorite.cityName) }
                    )
                }
            }
        }
    }
}

/**
 * Favorite location card component.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteLocationCard(
    favorite: FavoriteLocation,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = favorite.cityName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                favorite.country?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Lat: ${String.format("%.2f", favorite.latitude)}, " +
                            "Lon: ${String.format("%.2f", favorite.longitude)}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

