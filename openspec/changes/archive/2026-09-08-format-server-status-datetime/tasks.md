## 1. Backend

- [x] 1.1 Change `ServerStatus.dateTime` from `LocalDateTime` to `String` in
      `domain/ServerStatus.java` and verify it still compiles with no framework
      annotations
- [x] 1.2 In `ServerStatusService`, format `LocalDateTime.now(zoneId)` with
      `DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm:ss", Locale.ENGLISH)` before
      building `ServerStatus`, and verify `current()` returns a `dateTime` string
      shaped like `8 September 2026, 14:43:57`
- [x] 1.3 Update `ServerStatusControllerTest` to assert the new `dateTime` format
      (e.g. mock `current()` to return a known `ServerStatus` and assert
      `$.dateTime` equals the expected formatted string) and verify the test passes
