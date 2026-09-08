## Context

See proposal.md - Why. `ServerStatusService.current()` builds `ServerStatus` from
`LocalDateTime.now(zoneId)`; `ServerStatus.dateTime` is currently typed
`LocalDateTime` and relies on Spring Boot's default Jackson JSR-310 serialization,
which is what produces the ISO string with sub-second digits.

## Goals / Non-Goals

**Goals:**
- Format `dateTime` server-side into the exact human-readable string the spec
  requires, independent of Jackson's default date serialization
- Keep the format fixed and locale-independent of the JVM's default locale (so it
  doesn't silently change with the runtime environment)

**Non-Goals:**
- No i18n/localization of the date format - one fixed English format for everyone
- No API versioning for this breaking change - `/api/v1/server-status` changes
  in place, consistent with how the endpoint has no consumers yet outside this app

## Decisions

- **`ServerStatus.dateTime` becomes a `String`, formatted in the service before
  construction**, rather than keeping it a `LocalDateTime` and trying to control
  its JSON shape via Jackson annotations. Alternative considered: a custom Jackson
  serializer/`@JsonFormat` on the field - rejected as more moving parts than a
  one-line `DateTimeFormatter.format()` call for a single fixed format, and it
  keeps `ServerStatus` (a plain record) free of Jackson annotations.
- **`DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm:ss", Locale.ENGLISH)`**,
  applied to the same `LocalDateTime.now(zoneId)` the service already computes.
  `Locale.ENGLISH` is explicit so the month name doesn't vary with the JVM's
  default locale between environments.

## Risks / Trade-offs

- This is a breaking response shape change (`dateTime` was ISO-8601, parseable;
  now it's a display string) -> acceptable per the proposal: the field has no
  consumers today besides the frontpage, which already treats it as opaque text.

## Migration Plan

Single-commit, additive-to-deploy: update the service, update the test, redeploy.
No data migration. Rollback is reverting the commit - no persisted state depends on
the field's format.
