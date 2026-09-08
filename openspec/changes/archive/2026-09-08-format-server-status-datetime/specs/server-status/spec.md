## MODIFIED Requirements

### Requirement: Report server date, time, and zone

The system SHALL expose the server's current date and time together with the zone id
it is configured for. The date and time SHALL be formatted as a human-readable
string: day of month without a leading zero, full month name, four-digit year, a
comma, then 24-hour time to the second (e.g. `8 September 2026, 14:43:57`),
formatted in the configured zone.

#### Scenario: Fetching server status returns date, time, and zone

- **Given** the server is configured with a zone id
- **When** a client GETs `/api/v1/server-status`
- **Then** the response is `200` and `dateTime` reads like `8 September 2026,
  14:43:57` - no `T` separator and no sub-second digits - and the response includes
  that zone id (e.g. `Europe/Zurich`)
