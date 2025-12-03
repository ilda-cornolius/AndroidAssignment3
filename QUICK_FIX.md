# Quick Fix: "unable to resolve host" Error

## The Problem
You're seeing this error: **"unable to resolve host api.openweather.org"**

## The Solution (2 Steps)

### Step 1: Get Your API Key

1. Go to: https://openweathermap.org/api
2. Click "Sign Up" (it's free)
3. After signing up, go to "API keys" tab
4. Copy your API key (it looks like: `abc123def456ghi789...`)

**Note:** New API keys may take 10-15 minutes to activate.

### Step 2: Add Your API Key to the Code

1. Open this file:
   ```
   app/src/main/java/com/kenneth_demo/data/network/RetrofitClient.kt
   ```

2. Find line 21:
   ```kotlin
   const val API_KEY = "YOUR_API_KEY_HERE"
   ```

3. Replace it with your actual API key:
   ```kotlin
   const val API_KEY = "your_actual_api_key_here"
   ```

4. **Rebuild the app** (Build → Rebuild Project)

5. Run the app again

## Verify It Works

After adding your API key, the app should work. If you still see errors:

1. **Check the API key** - Make sure you copied it correctly (no extra spaces)
2. **Wait a few minutes** - New keys need time to activate
3. **Check internet** - Make sure your device/emulator has internet access
4. **Check the error message** - The app now shows more helpful messages

## Testing Your API Key

You can test your API key in a web browser:
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_API_KEY&units=metric
```

Replace `YOUR_API_KEY` with your actual key. You should see JSON weather data.

## Common Issues

- **Still seeing "unable to resolve host"**: Make sure you rebuilt the app after changing the API key
- **"401 Unauthorized"**: Your API key is invalid or not activated yet
- **"403 Forbidden"**: Your API key is blocked (contact OpenWeatherMap support)

## Need Help?

Check the detailed `SETUP_INSTRUCTIONS.md` file for more troubleshooting tips.

