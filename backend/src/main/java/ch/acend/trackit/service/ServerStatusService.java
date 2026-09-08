package ch.acend.trackit.service;

import ch.acend.trackit.domain.ServerStatus;
import ch.acend.trackit.domain.Weather;
import ch.acend.trackit.domain.WeatherCondition;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServerStatusService {

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm:ss", Locale.ENGLISH);

    private final ZoneId zoneId;

    public ServerStatusService(@Value("${trackit.zone-id}") String zoneId) {
        this.zoneId = ZoneId.of(zoneId);
    }

    public ServerStatus current() {
        return new ServerStatus(
                DATE_TIME_FORMAT.format(LocalDateTime.now(zoneId)),
                zoneId.getId(),
                new Weather(30, WeatherCondition.SUNNY));
    }
}
