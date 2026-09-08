package ch.acend.trackit.domain;

import java.time.LocalDateTime;

public record ServerStatus(LocalDateTime dateTime, String zoneId, Weather weather) {
}
