package com.siddhi.incident_platform.service.impl;

import com.siddhi.incident_platform.dto.IncidentResponse;
import com.siddhi.incident_platform.dto.CreateIncidentRequest;
import com.siddhi.incident_platform.repository.IncidentRepository;
import com.siddhi.incident_platform.repository.UserRepository;
import com.siddhi.incident_platform.service.IncidentService;

import com.siddhi.incident_platform.entity.Incident;
import com.siddhi.incident_platform.entity.User;
import com.siddhi.incident_platform.enums.IncidentStatus;
import com.siddhi.incident_platform.mapper.IncidentMapper;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    public IncidentServiceImpl(IncidentRepository incidentRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public IncidentResponse createIncident(CreateIncidentRequest request){
        User createdBy = userRepository.findById(request.getCreatedByUserId())
                .orElseThrow(()-> new RuntimeException("User not found with id:" + request.getCreatedByUserId()));

        Incident incident=Incident.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .severity(request.getSeverity())
                .incidentStatus(IncidentStatus.OPEN)
                .sourceType(request.getSourceType())
                .externalId(request.getExternalId())
                .affectedAsset(request.getAffectedAsset())
                .detectionLink(request.getDetectionLink())
                .createdBy(createdBy)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Incident savedIncident=incidentRepository.save(incident);

        return IncidentMapper.toIncidentResponse(savedIncident);
    }

    @Override
    public IncidentResponse getIncidentById(Long incidentId){
        Incident incident=incidentRepository.findById(incidentId)
                .orElseThrow(()-> new RuntimeException("Incident not found with id:" + incidentId));

        return IncidentMapper.toIncidentResponse(incident);
    }

    @Override
    public List<IncidentResponse> getAllIncidents(){
        List<Incident> incidents=incidentRepository.findAll();

        return incidents.stream()
                .map(IncidentMapper::toIncidentResponse)
                .collect(Collectors.toList());
    }
}
