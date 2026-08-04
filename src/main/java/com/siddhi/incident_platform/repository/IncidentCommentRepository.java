package com.siddhi.incident_platform.repository;

import com.siddhi.incident_platform.entity.Incident;
import com.siddhi.incident_platform.entity.IncidentComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentCommentRepository extends JpaRepository<IncidentComment, Long> {

    List<IncidentComment> findByIncidentOrderByCommentedAtDesc(Incident incident);


}
