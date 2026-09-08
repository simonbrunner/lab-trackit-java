## Why

The frontpage shows a task list and nothing else - no sense of "when" or "where" this
instance is running. A small status readout (server date/time, its timezone/location,
and current weather) gives the page a point of orientation. Weather has no backing
integration yet, so it starts hardcoded to keep the slice shippable; the shape is
built to hold a real value later without changing the contract.

## What Changes

- New read-only endpoint `GET /api/v1/server-status` returning the server's current
  date/time, its configured zone id (doubling as location and timezone, e.g.
  `Europe/Zurich`), and a weather reading (temperature in Celsius, condition).
- The zone id is server configuration (a property with a development default), not
  derived from the request or the client.
- Weather is hardcoded server-side to 30degC / `SUNNY` for now. The condition is a
  fixed enum so the contract already reflects "one of a known set", even though only
  one value is ever produced today.
- Frontend fetches this once when the frontpage loads and renders it as a static
  readout (no polling, no client-side ticking clock).

## Capabilities

### New Capabilities
- `server-status`: exposes the server's current date/time, timezone/location, and
  weather reading through a single read-only endpoint, and displays it on the
  frontpage.

### Modified Capabilities
(none)

## Impact

- Backend: new `ServerStatusController` (`web`), `ServerStatusService` (`service`),
  and domain records/enum (`domain`) - `ServerStatus`, `Weather`, `WeatherCondition`.
  No persistence, no migration - this is computed, not stored. One new configuration
  property (server zone id, e.g. `trackit.zone-id`) with a development default in
  `application.yml`.
- Frontend: new `api/serverStatus.ts` module and a small readout rendered on
  `TaskBoardView.vue` (the only existing route).
- No new dependency - `java.time` covers the date/time and zone handling.
