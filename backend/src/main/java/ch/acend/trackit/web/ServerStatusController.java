package ch.acend.trackit.web;

import ch.acend.trackit.domain.ServerStatus;
import ch.acend.trackit.service.ServerStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/server-status")
public class ServerStatusController {

    private final ServerStatusService serverStatusService;

    public ServerStatusController(ServerStatusService serverStatusService) {
        this.serverStatusService = serverStatusService;
    }

    @GetMapping
    public ServerStatus getServerStatus() {
        return serverStatusService.current();
    }
}
