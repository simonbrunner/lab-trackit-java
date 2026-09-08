# Spec: Server Status

The live spec for server status. Merged from change `add-server-status` when it was
archived on 8 September 2026. Every requirement is covered by a test in
`backend/src/test/java/ch/acend/trackit/web/ServerStatusControllerTest.java`.

Exposes the server's current date and time, its configured zone (which doubles as
location and timezone), and a weather reading, so the frontend can show a status
readout on the frontpage without the client guessing at any of it.

## Requirements

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

### Requirement: The zone id is server configuration, not client input

The zone id SHALL be read from server configuration and SHALL NOT vary with the
requesting client. It serves as both the displayed location and the timezone -
no separate location field exists.

#### Scenario: Zone id is independent of the caller

- **Given** the server is configured with a zone id
- **When** two different clients GET `/api/v1/server-status`, with or without a
  locale/timezone header of their own
- **Then** both responses report the same zone id

### Requirement: Report current weather

The system SHALL include a weather reading in the server status response, made of a
temperature in degrees Celsius and a condition drawn from a fixed set of values.

#### Scenario: Weather is present in the status response

- **When** a client GETs `/api/v1/server-status`
- **Then** the response includes a numeric temperature in degrees Celsius and a
  condition value from the fixed set

#### Scenario: Weather is currently a fixed reading

- **Given** no weather data source is wired up yet
- **When** a client GETs `/api/v1/server-status` at any time
- **Then** the temperature is `30` and the condition is `SUNNY`, the same for every
  request
