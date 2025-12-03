# OpenWeatherMap API Information

## Current Implementation

The app currently uses **Current Weather Data API** (free tier):
- Endpoint: `/data/2.5/weather`
- Works with city names directly
- Free tier: 60 calls/minute, 1,000,000 calls/month

## One Call API 3.0

**One Call API 3.0** is different:
- Requires subscription (1,000 free calls/day, then pay-as-you-go)
- Endpoint: `/data/3.0/onecall`
- Requires coordinates (lat/lon) - doesn't accept city names directly
- Provides: current, hourly forecast (48h), daily forecast (8 days)
- Different response structure

## What This Means

Your API key works with both APIs. The current implementation uses Current Weather API which:
- ✅ Works with free tier
- ✅ Accepts city names directly
- ✅ Simple response structure
- ✅ Perfect for assignment requirements

If you want to use One Call API 3.0 instead, we need to:
1. Add Geocoding API to convert city names → coordinates
2. Update data models for One Call API 3.0 response
3. Update UI to handle the different response structure

## Recommendation

For the assignment, **Current Weather API is sufficient**. One Call API 3.0 provides more data (hourly/daily forecasts) but requires subscription and more complex implementation.

Your current API key (`1e4444cf0ec61bfcebccf8038ce2ebde`) should work with Current Weather API without any subscription.

