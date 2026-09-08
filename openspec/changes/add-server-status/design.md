## Context

See proposal.md - Why. `TaskBoardView.vue` is currently the only frontend route.
Existing resources (`Task`, `Comment`) are persisted through JPA with a matching
`*Entity` class and a Flyway migration each. This capability has nothing to persist -
the response is computed per request from server configuration and a hardcoded
weather reading - so that pattern doesn't apply here.

## Goals / Non-Goals

**Goals:**
- One read-only endpoint delivering date/time, zone id, and weather in a single
  response
- Zone id configurable with a sensible development default, not hardcoded in code
- Weather condition typed as a closed set from day one, even though only `SUNNY` is
  ever produced today

**Non-Goals:**
- No real weather integration - no external API call, no new dependency
- No live-updating clock - the frontend renders a static snapshot fetched once on
  page load
- No per-user or per-request timezone/location - one server-wide zone id for
  everyone

## Decisions

- **Single merged `zoneId` field instead of separate location + timezone strings.**
  An IANA zone id (e.g. `Europe/Zurich`) already encodes both. Alternative
  considered: separate `location: String` + `timeZone: String` config properties -
  rejected as redundant and a source of copy/paste drift between the two.
- **Weather condition as a Java enum** (`WeatherCondition`, starting with `SUNNY`
  and naming the other values a simple forecast would realistically need) rather
  than a free string. Keeps the response contract closed even while only one value
  is ever produced. Alternative: plain `String` - rejected because nothing would
  constrain values once a real integration lands.
- **Config via a single Spring property** (`trackit.zone-id`) with a development
  default, read into the service via constructor injection with `@Value` -
  consistent with how the datasource URL/user/password are already sourced from
  environment variables with development defaults.
- **No entity, no repository, no migration.** The response is computed, not stored,
  so `domain/` gets plain records (`ServerStatus`, `Weather`) with no matching
  `*Entity` - there is nothing to persist.
- **No dto.** The endpoint takes no request body, so there's nothing to validate on
  the way in. The controller returns the `ServerStatus` domain record directly, in
  line with never returning a dto type from a controller.

## Risks / Trade-offs

- Hardcoded weather could be mistaken for a live reading -> the enum plus a test
  asserting the fixed `30`/`SUNNY` values makes the stub explicit, and the proposal
  states plainly that no integration exists yet.
- One server-wide zone id means every viewer sees the server's time/location, not
  their own -> acceptable per the confirmed scope ("server's location", not the
  viewer's).

## Migration Plan

Purely additive: new endpoint, new frontend fetch, one new config property with a
default. No schema change, no existing behavior touched, nothing to roll back beyond
reverting the change.
