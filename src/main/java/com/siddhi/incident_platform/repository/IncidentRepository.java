package com.siddhi.incident_platform.repository;

import com.siddhi.incident_platform.entity.Incident;
import com.siddhi.incident_platform.entity.User;
import com.siddhi.incident_platform.enums.IncidentStatus;
import com.siddhi.incident_platform.enums.SourceType;
import com.siddhi.incident_platform.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long>{

    List<Incident> findByIncidentStatus(IncidentStatus incidentStatus);

    List<Incident> findBySeverity(Severity severity);

    List<Incident> findBySourceType(SourceType sourceType);

    List<Incident> findByAssignedTo(User assignedTo);

    Optional<Incident> findBySourceTypeAndExternalId(SourceType sourceType, String externalId);

    boolean existsBySourceTypeAndExternalId(SourceType sourceType, String externalId);






}
