package com.siddhi.incident_platform.repository;

import com.siddhi.incident_platform.entity.IncidentAuditLog;
import com.siddhi.incident_platform.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentAuditLogRepository extends JpaRepository<IncidentAuditLog, Long> {

    List<IncidentAuditLog> findByIncidentOrderByPerformedAtDesc(Incident incident);


}
