# Quick Diagnosis: "Unable to Connect to Server"

## ⚠️ DO THIS FIRST - Test Your API Key

### Step 1: Test in Browser

**Open this URL in your browser (computer or Android device):**
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

**What you see:**

✅ **JSON data like:**
```json
{"coord":{"lon":-0.1257,"lat":51.5085},...}
```
→ **API KEY WORKS!** Problem is in Android app.

❌ **Error message:**
- `401 Unauthorized` → API key invalid/not activated
- `403 Forbidden` → API key blocked
- Network error → Check internet

### Step 2: Check Logcat for Exact Error

**In Android Studio:**
1. Open **Logcat** (bottom panel)
2. Filter by: `WeatherRepository` or `com.kenneth_demo`
3. Try to fetch weather in app
4. **Look for lines starting with `E/WeatherRepository`**
5. **Copy the error message**

The improved logging will show:
- Error type
- Error message
- API key (first 8 chars)

## Common Issues & Quick Fixes

### Issue 1: "Unable to resolve host"

**What it means:** Android can't find the server

**Fix:**
1. Check device internet (open browser → google.com)
2. Try different network (switch WiFi/Mobile data)
3. Restart device/emulator
4. Check DNS settings

### Issue 2: API Key Not Activated

**What it means:** New API keys need time

**Fix:**
- Wait 10-15 minutes after creating API key
- Check OpenWeatherMap dashboard for key status

### Issue 3: SSL/Certificate Error

**What it means:** Device date/time is wrong

**Fix:**
- Settings → Date & Time → Enable "Automatic date & time"
- Check date/time is correct
- Restart device

### Issue 4: Connection Timeout

**What it means:** Request is taking too long

**Fix:**
- Check internet speed
- Disable VPN if active
- Check firewall settings
- Try again later

## What to Share for Help

1. **Result of browser test** (does it show JSON or error?)
2. **Logcat error message** (copy from Logcat)
3. **Device type** (emulator or physical device)
4. **Android version**

## Current Setup

- ✅ API Key: `1e4444cf0ec61bfcebccf8038ce2ebde` (set)
- ✅ Base URL: `https://api.openweathermap.org/data/2.5/` (correct)
- ✅ Internet Permission: Added
- ✅ Network Security Config: Added

The code is correct. The issue is likely:
- API key not activated yet
- Network connectivity on device
- Device configuration (date/time, DNS)

**Test the API key in browser FIRST - this will tell us if it's the API key or the app!**

