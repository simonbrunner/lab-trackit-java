## Why

`GET /api/v1/server-status` currently returns `dateTime` as Java's default
`LocalDateTime` serialization, e.g. `2026-09-08T14:43:57.200739878` - a `T` separator
and up to nine digits of sub-second noise. It's meant to be read by a person on the
frontpage, not parsed by a downstream system, so it should read like a date, not a
serialization artifact.

## What Changes

- **BREAKING**: `dateTime` in the `GET /api/v1/server-status` response changes shape,
  from an ISO-8601 `LocalDateTime` string to a human-readable string in the format
  `8 September 2026, 14:43:57` (day without leading zero, full month name, year,
  comma, 24-hour time to the second) - formatted server-side in the configured zone.
- No other field of the response changes. `zoneId` and `weather` are unaffected.

## Capabilities

### New Capabilities
(none)

### Modified Capabilities
- `server-status`: the "Report server date, time, and zone" requirement's `dateTime`
  format changes from ISO-8601 to a human-readable string.

## Impact

- Backend: `ServerStatusService` formats the current date/time with a fixed
  `DateTimeFormatter` instead of returning the raw `LocalDateTime`. `ServerStatus`'s
  `dateTime` field becomes a `String`, matching how `zoneId` is already returned.
- Frontend: no change needed - `TaskBoardView.vue` already renders `dateTime` as
  opaque text; it will simply display the new, more readable string.
- No new dependency - `java.time.format.DateTimeFormatter` is built in.
