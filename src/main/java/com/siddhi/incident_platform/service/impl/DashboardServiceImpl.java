package com.siddhi.incident_platform.service.impl;

import com.siddhi.incident_platform.dto.DashboardSummaryResponse;
import com.siddhi.incident_platform.enums.IncidentStatus;
import com.siddhi.incident_platform.enums.Severity;
import com.siddhi.incident_platform.service.DashboardService;
import com.siddhi.incident_platform.repository.IncidentRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final IncidentRepository incidentRepository;

    public DashboardServiceImpl(IncidentRepository incidentRepository)
    {
        this.incidentRepository=incidentRepository;
    }

    @Override
    public DashboardSummaryResponse getDashboardSummary(){

        long totalIncidents=incidentRepository.count();

        long openIncidents=incidentRepository.countByIncidentStatus(IncidentStatus.OPEN);
        long inProgressIncidents=incidentRepository.countByIncidentStatus(IncidentStatus.IN_PROGRESS);
        long onHoldIncidents=incidentRepository.countByIncidentStatus(IncidentStatus.ON_HOLD);
        long resolvedIncidents=incidentRepository.countByIncidentStatus(IncidentStatus.RESOLVED);
        long closedIncidents=incidentRepository.countByIncidentStatus(IncidentStatus.CLOSED);

        long criticalIncidents=incidentRepository.countBySeverity(Severity.CRITICAL);
        long highIncidents=incidentRepository.countBySeverity(Severity.HIGH);
        long mediumIncidents=incidentRepository.countBySeverity(Severity.MEDIUM);
        long lowIncidents=incidentRepository.countBySeverity(Severity.LOW);

        return DashboardSummaryResponse.builder()
                .totalIncidents(totalIncidents)
                .openIncidents(openIncidents)
                .inProgressIncidents(inProgressIncidents)
                .onHoldIncidents(onHoldIncidents)
                .resolvedIncidents(resolvedIncidents)
                .closedIncidents(closedIncidents)
                .criticalIncidents(criticalIncidents)
                .highIncidents(highIncidents)
                .mediumIncidents(mediumIncidents)
                .lowIncidents(lowIncidents)
                .build();
    }
}
