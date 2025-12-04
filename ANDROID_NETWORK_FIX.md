# Android Network Issue - Step-by-Step Fix

## Critical Checks for Android

### 1. Check Logcat for Exact Error (MOST IMPORTANT)

**In Android Studio:**
1. Open **Logcat** (bottom panel)
2. Clear the log
3. Filter by: `com.kenneth_demo` or `Weather`
4. **Run the app and try to fetch weather**
5. **Copy the EXACT error message**

Common error messages you might see:
- `Unable to resolve host "api.openweathermap.org"` → DNS/Network issue
- `401 Unauthorized` → API key problem
- `SSLHandshakeException` → Certificate/Date issue
- `SocketTimeoutException` → Connection timeout
- `NetworkSecurityException` → Network security config issue
- `java.net.UnknownHostException` → DNS resolution failed

### 2. Test API Key on Android Device/Emulator Browser

**On your Android device/emulator:**
1. Open Chrome/Browser app
2. Visit this URL:
   ```
   https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
   ```

**If browser shows:**
- ✅ JSON data → API key works, issue is in app
- ❌ Error page → API key or network issue

### 3. Check Device Date/Time

**Android Settings:**
1. Settings → System → Date & Time
2. Enable "Automatic date & time"
3. Enable "Automatic time zone"
4. **Incorrect date/time causes SSL errors!**

### 4. Verify Internet Connection on Device

**On Emulator:**
- Settings → Network & Internet → Check WiFi/Mobile data is ON
- Open Browser → Visit google.com
- If browser doesn't work → Network problem

**On Physical Device:**
- Check WiFi/Mobile data is enabled
- Open Browser → Visit google.com
- Try different network (switch WiFi/Mobile data)

### 5. Network Security Config Added

I've added a network security configuration file. **Rebuild the app** after this change:

1. **Clean Project:** Build → Clean Project
2. **Rebuild:** Build → Rebuild Project
3. **Run:** Run → Run 'app'

### 6. Check API Key is Correct

**File:** `app/src/main/java/com/kenneth_demo/data/network/RetrofitClient.kt`
**Line 23 should show:**
```kotlin
const val API_KEY = "1e4444cf0ec61bfcebccf8038ce2ebde"
```

### 7. Test on Different Network

- Try on WiFi vs Mobile data
- Try on different WiFi network
- Try disabling VPN if active

## Quick Test Commands

### Test 1: From Computer (using curl)
```bash
curl "https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric"
```

If this works → API key is fine, issue is Android-specific

### Test 2: From Android Device Browser
Open browser on device and visit:
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

## Common Android-Specific Issues

### Issue: Emulator Has No Internet
**Symptoms:**
- Browser on emulator can't load websites
- App can't connect

**Fix:**
1. Settings → Network & Internet → Reset network settings
2. Or create new emulator instance
3. Or try on physical device

### Issue: SSL/Certificate Error
**Symptoms:**
- Logcat shows `SSLHandshakeException`
- `CertificateException`

**Fix:**
1. Check device date/time is correct
2. Settings → System → Date & Time → Enable automatic
3. Restart device/emulator

### Issue: Network Security Config Error
**Symptoms:**
- `NetworkSecurityException`
- `Cleartext traffic not permitted`

**Fix:**
- I've added network security config (already done)
- Rebuild the app

### Issue: API Key Not Activated
**Symptoms:**
- Browser test shows `401 Unauthorized`
- Logcat shows authentication errors

**Fix:**
- Wait 10-15 minutes for new API keys
- Check OpenWeatherMap dashboard for key status

## What to Do Right Now

1. **Check Logcat** - Get the exact error message
2. **Test API in device browser** - See if it works there
3. **Check date/time** - Make sure it's correct
4. **Rebuild app** - Clean and rebuild after network security config
5. **Try different network** - Switch WiFi/Mobile data

## Share This Information

Please share:
1. **Exact error message from Logcat**
2. **Does browser on device work?** (when testing the API URL)
3. **Device type** (emulator or physical device)
4. **Android version**

This will help diagnose the exact issue!

