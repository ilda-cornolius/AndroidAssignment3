# Fix: "Unable to resolve host" - DNS Error

## ✅ Good News!

Your API call format is **CORRECT**! I can see in the logs:
```
GET https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

This matches the documentation perfectly!

## The Problem

**Error:** `UnknownHostException: Unable to resolve host "api.openweathermap.org"`

**What it means:** Your Android device/emulator can't convert the domain name to an IP address (DNS resolution failure).

## Solutions

### Solution 1: Fix Emulator Network (MOST COMMON)

**If you're using an Android Emulator:**

1. **Restart the emulator:**
   - Close the emulator completely
   - Restart it from Android Studio

2. **Check emulator network settings:**
   - Settings → Network & Internet
   - Make sure WiFi or Mobile data is enabled

3. **Reset emulator network:**
   - Settings → Network & Internet → Reset network settings
   - Or: Settings → System → Reset → Reset network settings

4. **Create a new emulator:**
   - If the above doesn't work, create a fresh emulator instance

### Solution 2: Test on Physical Device

**Try on a real Android device instead:**
- Physical devices usually have better network connectivity
- USB debugging or wireless debugging

### Solution 3: Check Device Internet

**On your device/emulator:**
1. Open Browser app
2. Visit: `https://google.com`
3. If it doesn't load → Network problem (DNS or internet)
4. If it loads → Internet works, DNS issue specific to the app

### Solution 4: Change DNS Settings (Emulator)

**In Android Emulator:**
1. Settings → Network & Internet → WiFi
2. Long press your network
3. Modify network
4. Advanced options
5. Change DNS to: `8.8.8.8` (Google DNS) or `1.1.1.1` (Cloudflare DNS)

### Solution 5: Restart Computer/Emulator

**Sometimes a simple restart fixes DNS issues:**
- Restart Android Studio
- Restart the emulator
- Or restart your computer

## Quick Test

**On your device/emulator browser:**
1. Open Chrome/Browser
2. Visit: `https://api.openweathermap.org`
3. If it loads → DNS works for browser
4. If it doesn't → DNS issue on device

## Why This Happens

**Common causes:**
1. **Emulator network issues** - Emulator DNS not configured
2. **VPN/Proxy** - Interfering with DNS resolution
3. **Firewall** - Blocking DNS queries
4. **Network configuration** - DNS servers not set

## Most Likely Fix

**If using emulator:**
1. Close emulator completely
2. Restart Android Studio
3. Create/start a fresh emulator
4. Try the app again

**Or test on a physical device** - this usually works better for network issues.

## The Code is Perfect!

Your code is working correctly - the request is being made properly. This is purely a device/emulator DNS resolution issue. The API call format matches the documentation exactly!

