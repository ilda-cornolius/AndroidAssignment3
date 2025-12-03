# Weather Tracking App - Assignment 3

A comprehensive Android weather tracking application built with Jetpack Compose, Retrofit, Room, and MVVM architecture.

## Features

- **Networking with Retrofit and Kotlin Coroutines**: Fetch weather data from OpenWeatherMap API
- **Jetpack Navigation**: Navigate between different screens with argument passing
- **Room Database**: Local storage for favorite locations and cached weather data
- **MVVM Architecture**: Clean separation of concerns with ViewModels and Repository pattern
- **Responsive UI**: Material Design 3 components with proper alignment and user-friendly interface

## Setup Instructions

### 1. Get OpenWeatherMap API Key

1. Visit [OpenWeatherMap](https://openweathermap.org/api)
2. Sign up for a free account
3. Get your API key from the dashboard
4. Replace `YOUR_API_KEY_HERE` in `RetrofitClient.kt` with your actual API key

```kotlin
// File: app/src/main/java/com/kenneth_demo/data/network/RetrofitClient.kt
const val API_KEY = "YOUR_ACTUAL_API_KEY_HERE"
```

### 2. Project Structure

```
app/
├── data/
│   ├── local/
│   │   ├── entity/          # Room entities
│   │   ├── dao/             # Data Access Objects
│   │   └── WeatherDatabase.kt
│   ├── model/               # API response models
│   ├── network/             # Retrofit setup
│   └── repository/          # Repository pattern
├── ui/
│   ├── navigation/          # Navigation setup
│   ├── screen/              # Compose screens
│   ├── theme/               # App theming
│   └── viewmodel/           # ViewModels
└── WeatherApplication.kt    # Dependency injection
```

### 3. Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture:

- **Model**: Data models, entities, and API services
- **View**: Compose UI screens
- **ViewModel**: Business logic and UI state management
- **Repository**: Abstracts data access (network + local database)

## Dependencies

- Retrofit 2.11.0 - Network calls
- Room 2.6.1 - Local database
- Navigation Compose 2.8.3 - Navigation
- Kotlin Coroutines - Asynchronous operations
- Material 3 - UI components
- ViewModel & LiveData - Architecture components

## Screens

1. **Home Screen**: Default weather display with navigation options
2. **Search Screen**: Search for weather by city name
3. **Detail Screen**: Comprehensive weather information
4. **Favorites Screen**: List of saved favorite locations

## Usage

1. Launch the app
2. Home screen displays default weather (London)
3. Use "Search" button to find weather for any city
4. Tap on any location to view detailed weather information
5. Add locations to favorites for quick access
6. View all favorites from the Favorites screen

## Room Database

The app uses Room for local storage:

- **FavoriteLocation Entity**: Stores user's favorite locations
- **WeatherDataEntity**: Caches weather data for offline access (30-minute cache)

## Network Features

- Automatic error handling
- Cache-first approach with network fallback
- Network request logging for debugging
- 30-second timeout for requests

## Testing with Resizable Emulator

To test responsive navigation:

1. Open Android Studio
2. Create a resizable emulator (Foldable or Tablet)
3. Run the app on the emulator
4. Resize the emulator to test different screen sizes
5. Verify navigation works correctly on all sizes

## Notes

- API key must be added before the app can fetch weather data
- Default city is "London" - can be changed in `HomeScreen.kt`
- Weather data is cached for 30 minutes
- Favorite locations persist across app restarts

## Requirements Met

✅ Retrofit setup with Kotlin Coroutines  
✅ Network calls with error handling  
✅ Jetpack Navigation with argument passing  
✅ Room database setup with entities and DAOs  
✅ Repository pattern implementation  
✅ MVVM architecture with ViewModels  
✅ Responsive UI design  
✅ Material Design 3 components  
✅ Local data persistence  
✅ Favorite locations management  

