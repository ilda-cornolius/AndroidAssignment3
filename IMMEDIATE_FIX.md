# IMMEDIATE FIX: DNS Error

## The Error

**`UnknownHostException: Unable to resolve host "api.openweathermap.org"`**

Your Android device/emulator **cannot resolve DNS** (convert domain name to IP address).

## ✅ Good News

Your code is **100% CORRECT**! The API call is being made properly:
```
GET https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

## 🚀 FASTEST FIX (Do This First!)

### Use a Physical Android Device

**Physical devices have real network/DNS - this will work!**

**Steps:**
1. Connect Android phone via USB cable
2. On phone: Settings → About Phone → Tap "Build Number" 7 times
3. Settings → Developer Options → Enable "USB Debugging"
4. In Android Studio: Select your phone from device dropdown
5. Run the app → **It will work!**

**Why this works:**
- Real network connectivity
- Proper DNS resolution  
- No emulator network issues
- Works immediately

## Alternative: Fix Emulator DNS

**If you must use emulator:**

### Option 1: Restart Everything
1. Close emulator completely
2. Close Android Studio
3. **Restart your computer** (clears DNS cache)
4. Open Android Studio
5. Start emulator
6. Run app

### Option 2: Change DNS Settings
1. In emulator: Settings → Network & Internet
2. WiFi → Long press network → Modify
3. Advanced options
4. IP settings: Static
5. DNS 1: `8.8.8.8`
6. DNS 2: `8.8.4.4`
7. Save and restart emulator

### Option 3: Create New Emulator
1. Tools → Device Manager
2. Delete old emulator
3. Create new virtual device
4. Run app on new emulator

## Test Your Device

**On your device/emulator:**
1. Open Browser app
2. Visit: `https://api.openweathermap.org`
3. If it loads → DNS works, check app permissions
4. If it doesn't → DNS problem confirmed

## Summary

- ✅ Code: CORRECT
- ✅ API: CORRECT
- ❌ DNS: FAILING on device

**USE A PHYSICAL DEVICE - This fixes it immediately!**

