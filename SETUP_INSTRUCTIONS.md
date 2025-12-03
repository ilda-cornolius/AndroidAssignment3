# Setup Instructions - Fix Network Errors

## Issue: "unable to resolve host api.openweather.org"

This error typically occurs due to one of the following reasons:

### 1. API Key Not Set (MOST COMMON)

**You MUST set your OpenWeatherMap API key before the app can work!**

1. Get a free API key:
   - Go to https://openweathermap.org/api
   - Sign up for a free account
   - Go to "API keys" section
   - Copy your API key

2. Add the API key to the code:
   - Open: `app/src/main/java/com/kenneth_demo/data/network/RetrofitClient.kt`
   - Find line 21: `const val API_KEY = "YOUR_API_KEY_HERE"`
   - Replace `YOUR_API_KEY_HERE` with your actual API key
   - Example: `const val API_KEY = "abc123def456ghi789"`

3. Rebuild the app after changing the API key

### 2. Network/Internet Issues

- Make sure your device/emulator has internet connectivity
- Check if you can access https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_KEY in a browser
- If using an emulator, ensure it has network access

### 3. Android Network Security

If you're on Android 9+ (API 28+), you may need to allow cleartext traffic. However, since we're using HTTPS, this shouldn't be necessary.

### 4. Verify the Base URL

The base URL should be exactly:
```
https://api.openweathermap.org/data/2.5/
```

If you see "api.openweather.org" (missing "map"), that's incorrect. The correct domain is "api.openweathermap.org".

### Quick Check

1. Open `RetrofitClient.kt`
2. Verify line 16 shows: `private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"`
3. Verify line 21 has your actual API key (not "YOUR_API_KEY_HERE")
4. Rebuild and run the app

## Testing Your API Key

Once you have your API key, test it in a browser:
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_API_KEY&units=metric
```

Replace `YOUR_API_KEY` with your actual key. You should see JSON weather data.

If you get an error, check:
- Is the API key activated? (new keys may take a few minutes)
- Is the API key valid?
- Are you using the correct URL format?

## Common Error Messages

- **"unable to resolve host"**: Usually means API key not set or network issue
- **"401 Unauthorized"**: API key is invalid or not set
- **"404 Not Found"**: URL might be incorrect
- **"403 Forbidden"**: API key is blocked or invalid

