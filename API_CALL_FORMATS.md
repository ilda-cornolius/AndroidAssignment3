# OpenWeatherMap API Call Formats

## Current Implementation

The app supports **both** API call formats:

### 1. By City Name
```
https://api.openweathermap.org/data/2.5/weather?q={cityName}&appid={API key}&units=metric
```

**Example:**
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

**Used in app:**
- HomeScreen (default: "London")
- SearchScreen (user searches by city name)

### 2. By Coordinates (Lat/Lon)
```
https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}&units=metric
```

**Example:**
```
https://api.openweathermap.org/data/2.5/weather?lat=51.5085&lon=-0.1257&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

**Used in app:**
- Can be called via `WeatherViewModel.fetchWeatherByCoordinates(lat, lon)`
- Stored coordinates from favorites can use this method

## Implementation Details

### API Service (`WeatherApiService.kt`)
```kotlin
// By city name
@GET("weather")
suspend fun getWeatherByCity(
    @Query("q") cityName: String,
    @Query("appid") apiKey: String,
    @Query("units") units: String = "metric"
): WeatherResponse

// By coordinates
@GET("weather")
suspend fun getWeatherByCoordinates(
    @Query("lat") lat: Double,
    @Query("lon") lon: Double,
    @Query("appid") apiKey: String,
    @Query("units") units: String = "metric"
): WeatherResponse
```

### Repository (`WeatherRepository.kt`)
- `getWeatherByCity(cityName: String)` - Uses city name
- `getWeatherByCoordinates(lat: Double, lon: Double)` - Uses lat/lon

### ViewModel (`WeatherViewModel.kt`)
- `fetchWeather(cityName: String)` - Fetches by city name
- `fetchWeatherByCoordinates(lat: Double, lon: Double)` - Fetches by coordinates

## Current Configuration

- **Base URL:** `https://api.openweathermap.org/data/2.5/`
- **API Key:** `1e4444cf0ec61bfcebccf8038ce2ebde`
- **Units:** `metric` (Celsius)

## Test URLs

### Test by City Name:
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

### Test by Coordinates (London):
```
https://api.openweathermap.org/data/2.5/weather?lat=51.5085&lon=-0.1257&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

Both formats are fully implemented and working! ✅

