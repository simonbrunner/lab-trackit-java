## 1. Backend domain and configuration

- [ ] 1.1 Add `WeatherCondition` enum in `domain` (starting with `SUNNY` plus the
      other values a simple forecast needs) and verify it compiles with no
      framework annotations
- [ ] 1.2 Add `Weather(int temperatureCelsius, WeatherCondition condition)` and
      `ServerStatus(LocalDateTime dateTime, String zoneId, Weather weather)` records
      in `domain` and verify they compile with no framework annotations
- [ ] 1.3 Add `trackit.zone-id` property to `application.yml` with a development
      default (e.g. `Europe/Zurich`) and verify the app starts locally with the
      default in place

## 2. Backend service and endpoint

- [ ] 2.1 Add `ServerStatusService` in `service`, constructor-injected with the
      configured zone id, returning a `ServerStatus` built from
      `LocalDateTime.now(ZoneId.of(zoneId))` and a hardcoded `Weather(30, SUNNY)`
- [ ] 2.2 Add `ServerStatusController` in `web`, `@RestController` mapped to
      `/api/v1/server-status`, with a single `GET` returning the `ServerStatus`
      domain record from the service
- [ ] 2.3 Add a `@WebMvcTest(ServerStatusController.class)` test with `MockMvc`
      covering: `GET /api/v1/server-status` returns `200` with `dateTime`, `zoneId`,
      and a `weather` object whose `temperatureCelsius` is `30` and `condition` is
      `SUNNY`, and verify it passes

## 3. Frontend

- [ ] 3.1 Add `api/serverStatus.ts` exporting a `ServerStatus` type mirroring the
      backend record field for field and a `getServerStatus()` function calling
      `GET /server-status`, and verify the type matches the backend response shape
- [ ] 3.2 Fetch the server status once in `TaskBoardView.vue` on mount, alongside
      the existing task list fetch, owning its own loading/error state separate
      from the task list's
- [ ] 3.3 Render the status readout on the page (date/time, zone id, temperature,
      condition) as a static snapshot with no polling or client-side ticking, and
      verify it renders correctly against a running backend, including when the
      fetch fails
