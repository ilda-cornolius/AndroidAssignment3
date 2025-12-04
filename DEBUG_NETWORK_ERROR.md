# Debug: "Can't Connect to Network" Error

## Immediate Steps to Fix

### Step 1: Test API Key in Browser (CRITICAL)

**Open this URL in your browser:**
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

**What you should see:**
- ✅ JSON data with weather information = API key works!
- ❌ Error message = API key issue

**Common browser errors:**
- `401 Unauthorized` = API key is invalid or not activated
- `403 Forbidden` = API key is blocked
- `404 Not Found` = URL issue
- `Network error` = Internet connectivity issue

### Step 2: Verify Current Configuration

**Check `RetrofitClient.kt` (line 23):**
```kotlin
const val API_KEY = "1e4444cf0ec61bfcebccf8038ce2ebde"
```

**Check Base URL (line 19):**
```kotlin
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
```

### Step 3: Check Device/Emulator Internet

**On Emulator:**
1. Open Settings → Network & Internet
2. Make sure WiFi or Mobile data is ON
3. Open Browser in emulator
4. Try visiting: https://google.com
5. If browser doesn't work → Network issue

**On Physical Device:**
1. Check WiFi/Mobile data is enabled
2. Try opening browser and visiting google.com
3. If browser doesn't work → Network issue

### Step 4: Check Logcat for Detailed Errors

**In Android Studio:**
1. Open Logcat (bottom panel)
2. Filter by your app package: `com.kenneth_demo`
3. Look for errors when you try to fetch weather
4. Copy the exact error message

**What to look for:**
- `Unable to resolve host` = DNS/Network issue
- `401` = API key problem
- `SSLHandshakeException` = Certificate/Date issue
- `SocketTimeoutException` = Connection timeout

### Step 5: Rebuild and Test

1. **Clean Project:**
   - Build → Clean Project

2. **Rebuild:**
   - Build → Rebuild Project

3. **Run:**
   - Run → Run 'app'

## Common Issues & Quick Fixes

### Issue: API Key Not Activated
**Symptom:** Browser test shows 401 Unauthorized

**Fix:**
- New API keys take 10-15 minutes to activate
- Wait 15 minutes and try again
- Check OpenWeatherMap dashboard for key status

### Issue: No Internet Connection
**Symptom:** Browser can't load any website

**Fix:**
- Check WiFi/Mobile data is enabled
- Restart device/emulator
- Try different network (mobile data vs WiFi)

### Issue: Emulator Network Issues
**Symptom:** Browser works on host computer but not emulator

**Fix:**
- Settings → Network & Internet → Reset network settings
- Or create new emulator instance

### Issue: DNS Resolution Failed
**Symptom:** "Unable to resolve host api.openweathermap.org"

**Fix:**
- Check internet connection
- Try using mobile data instead of WiFi
- Restart device/emulator

### Issue: SSL/Certificate Error
**Symptom:** Certificate validation failed

**Fix:**
- Check device date/time is correct
- Settings → Date & Time → Auto date/time

## Test URLs

### Test 1: Simple API Call
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

### Test 2: By Coordinates (London)
```
https://api.openweathermap.org/data/2.5/weather?lat=51.5085&lon=-0.1257&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

### Test 3: Check API Status
- Visit: https://openweathermap.org/api
- Log in to your account
- Check API key status in dashboard

## Still Not Working?

1. **Get the exact error message from Logcat**
2. **Test API key in browser first**
3. **Check if other apps can access internet**
4. **Try on a different device/emulator**

## Need More Help?

Share:
- Exact error message from Logcat
- Result of browser API test
- Whether other apps have internet access
- Device type (emulator/physical)

This will help diagnose the exact issue!

