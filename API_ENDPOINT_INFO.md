# API Endpoint Information

## Current Setup

**Base URL:** `https://api.openweathermap.org/data/2.5/`  
**API Key:** `1e4444cf0ec61bfcebccf8038ce2ebde`

## Important Note

The standard OpenWeatherMap API uses:
- ✅ **Correct:** `api.openweathermap.org` 
- ❌ **Incorrect:** `home.openweather.org`

If you mentioned `home.openweather.org`, this might be:
1. A typo or confusion
2. A different service/self-hosted instance
3. A redirect or alias

## Standard OpenWeatherMap Endpoints

### Current Weather API (What the app uses)
- Base: `https://api.openweathermap.org/data/2.5/`
- Endpoint: `/weather`
- Full URL: `https://api.openweathermap.org/data/2.5/weather`

### One Call API 3.0
- Base: `https://api.openweathermap.org/data/3.0/`
- Endpoint: `/onecall`
- Full URL: `https://api.openweathermap.org/data/3.0/onecall`

## Test Your API Key

Test with your API key:
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

If this works in your browser, the setup is correct!

