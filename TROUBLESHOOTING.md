# Troubleshooting: "Unable to Connect" Error

## Quick Diagnostic Steps

### Step 1: Verify API Key is Set

1. Open: `app/src/main/java/com/kenneth_demo/data/network/RetrofitClient.kt`
2. Check line 21 - should show:
   ```kotlin
   const val API_KEY = "1e4444cf0ec61bfcebccf8038ce2ebde"
   ```
3. If it shows `"YOUR_API_KEY_HERE"`, replace it with your actual key

### Step 2: Test API Key in Browser

Open this URL in your browser (replace with your API key if different):
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

**Expected Result:** You should see JSON data like:
```json
{"coord":{"lon":-0.1257,"lat":51.5085},"weather":[...],"base":"stations",...}
```

**If you see an error:**
- **401 Unauthorized**: API key is invalid or not activated
- **403 Forbidden**: API key is blocked
- **404 Not Found**: URL is incorrect
- **Network error**: Check your internet connection

### Step 3: Check Network Connectivity

**On Emulator:**
- Settings → Network & Internet → Check if WiFi/Mobile data is enabled
- Try opening a browser in the emulator and visit google.com

**On Physical Device:**
- Make sure WiFi or Mobile data is enabled
- Try opening a browser and visit google.com

### Step 4: Verify App Has Internet Permission

Check `app/src/main/AndroidManifest.xml` should have:
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

### Step 5: Check Logs

In Android Studio:
1. Open Logcat (bottom panel)
2. Filter by "Weather" or your app package name
3. Look for error messages starting with "Error:" or network exceptions
4. The detailed error will show what's wrong

## Common Issues and Solutions

### Issue 1: "Unable to resolve host"
**Causes:**
- No internet connection
- DNS issues
- API key not set

**Solutions:**
- Check internet connection
- Verify API key is set
- Try using mobile data instead of WiFi (or vice versa)

### Issue 2: "401 Unauthorized"
**Causes:**
- Invalid API key
- API key not activated yet (new keys need 10-15 minutes)

**Solutions:**
- Verify API key is correct
- Wait 10-15 minutes for new keys to activate
- Check OpenWeatherMap dashboard for key status

### Issue 3: Connection Timeout
**Causes:**
- Slow internet connection
- Firewall blocking requests
- VPN interfering

**Solutions:**
- Check internet speed
- Disable VPN temporarily
- Check firewall settings

### Issue 4: SSL/Certificate Error
**Causes:**
- Device date/time is incorrect
- Certificate issues

**Solutions:**
- Set device date/time correctly
- Check system clock settings

## Testing Your Setup

### Test 1: API Key Test
Run this in browser:
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

### Test 2: Network Test
Try this simpler endpoint:
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde
```

### Test 3: App Logs
Check Logcat for:
- Network request details
- Response codes
- Error stack traces

## Still Not Working?

1. **Rebuild the app:**
   - Build → Clean Project
   - Build → Rebuild Project
   - Run the app again

2. **Check OpenWeatherMap account:**
   - Log in to https://openweathermap.org
   - Check if API key is active
   - Check if there are any account limits

3. **Try a different city:**
   - Some city names might not be recognized
   - Try: "London", "New York", "Tokyo", "Paris"

4. **Wait and retry:**
   - New API keys can take 10-15 minutes to activate
   - Wait a few minutes and try again

## Getting More Help

If you see a specific error message in the app, note it down:
- The exact error text
- What screen you're on
- What you were trying to do

This will help diagnose the issue more precisely.

