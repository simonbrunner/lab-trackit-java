package ch.acend.trackit.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.acend.trackit.domain.ServerStatus;
import ch.acend.trackit.domain.Weather;
import ch.acend.trackit.domain.WeatherCondition;
import ch.acend.trackit.service.ServerStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ServerStatusController.class)
class ServerStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServerStatusService serverStatusService;

    @Test
    void getServerStatusReturnsDateTimeZoneAndWeather() throws Exception {
        when(serverStatusService.current())
                .thenReturn(
                        new ServerStatus(
                                "8 September 2026, 14:32:10",
                                "Europe/Zurich",
                                new Weather(30, WeatherCondition.SUNNY)));

        mockMvc.perform(get("/api/v1/server-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dateTime").value("8 September 2026, 14:32:10"))
                .andExpect(jsonPath("$.zoneId").value("Europe/Zurich"))
                .andExpect(jsonPath("$.weather.temperatureCelsius").value(30))
                .andExpect(jsonPath("$.weather.condition").value("SUNNY"));
    }
}
