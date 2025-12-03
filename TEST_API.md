# Test Your API Setup

## Test URL

Open this URL in your browser to test your API key:

```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=1e4444cf0ec61bfcebccf8038ce2ebde&units=metric
```

## Expected Result

You should see JSON data like:
```json
{
  "coord": {"lon": -0.1257, "lat": 51.5085},
  "weather": [...],
  "base": "stations",
  "main": {...},
  "name": "London",
  ...
}
```

## If You Get an Error

- **401 Unauthorized**: API key is invalid or not activated
- **403 Forbidden**: API key is blocked
- **404 Not Found**: URL is incorrect
- **Network error**: Check internet connection

## Current Configuration

The app is configured with:
- ✅ Base URL: `https://api.openweathermap.org/data/2.5/`
- ✅ API Key: `1e4444cf0ec61bfcebccf8038ce2ebde`

This should work! Try the test URL above first.

