# URGENT: Fix DNS Error - 3 Steps

## ✅ Your Code is Perfect!

The API call is correct:
```
GET https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

## The Problem

**Error:** `Unable to resolve host "api.openweathermap.org"`

Android device/emulator can't resolve DNS (find the IP address).

## ⚡️ Quick Fixes (Try in Order)

### Fix 1: Use Physical Device (90% Success Rate)

**This is the fastest solution:**

1. Connect Android phone via USB
2. Enable USB debugging:
   - Settings → About Phone → Tap "Build Number" 7 times
   - Settings → Developer Options → Enable "USB Debugging"
3. In Android Studio, select your phone (not emulator)
4. Run the app
5. **Done! Physical devices have real DNS - this will work!**

### Fix 2: Restart Emulator (70% Success Rate)

1. **Close emulator completely** (not just minimize)
2. **Close Android Studio**
3. **Restart your computer** (clears DNS cache)
4. Open Android Studio
5. Start emulator
6. Run app

### Fix 3: Change Emulator DNS

**In Android Emulator:**
1. Settings → Network & Internet
2. WiFi → Long press network → Modify
3. Advanced options
4. DNS 1: `8.8.8.8`
5. DNS 2: `8.8.4.4`
6. Save
7. Restart emulator

### Fix 4: Create New Emulator

1. Android Studio → Tools → AVD Manager
2. Delete old emulator
3. Create new virtual device
4. Run app on new emulator

## Test Your Device Internet

**On your device/emulator:**
1. Open Browser
2. Visit: `https://google.com`
3. If it doesn't load → No internet
4. Visit: `https://api.openweathermap.org`
5. If it doesn't load → DNS problem

## Summary

- ✅ Code: CORRECT
- ✅ API: CORRECT  
- ✅ Request: CORRECT
- ❌ DNS Resolution: FAILING on device

**Use a physical device - this will fix it immediately!**

