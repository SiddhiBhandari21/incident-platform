package com.siddhi.incident_platform.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryResponse {

    private long totalIncidents;

    private long openIncidents;

    private long inProgressIncidents;

    private long onHoldIncidents;

    private long resolvedIncidents;

    private long closedIncidents;

    private long criticalIncidents;

    private long highIncidents;

    private long lowIncidents;

    private long mediumIncidents;
}
