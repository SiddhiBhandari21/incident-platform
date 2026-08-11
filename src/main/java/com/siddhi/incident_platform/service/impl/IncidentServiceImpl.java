package com.siddhi.incident_platform.service.impl;

import com.siddhi.incident_platform.enums.Severity;
import com.siddhi.incident_platform.enums.IncidentStatus;
import com.siddhi.incident_platform.enums.NotificationType;
import com.siddhi.incident_platform.dto.IncidentResponse;
import com.siddhi.incident_platform.dto.CreateIncidentRequest;
import com.siddhi.incident_platform.dto.UpdateIncidentStatusRequest;
import com.siddhi.incident_platform.dto.AssignIncidentRequest;
import com.siddhi.incident_platform.dto.AddIncidentCommentRequest;
import com.siddhi.incident_platform.dto.IncidentCommentResponse;
import com.siddhi.incident_platform.dto.AuditLogResponse;
import com.siddhi.incident_platform.repository.IncidentRepository;
import com.siddhi.incident_platform.repository.UserRepository;
import com.siddhi.incident_platform.repository.IncidentAuditLogRepository;
import com.siddhi.incident_platform.repository.IncidentCommentRepository;
import com.siddhi.incident_platform.repository.NotificationRepository;
import com.siddhi.incident_platform.service.IncidentService;
import com.siddhi.incident_platform.entity.Incident;
import com.siddhi.incident_platform.entity.User;
import com.siddhi.incident_platform.entity.IncidentAuditLog;
import com.siddhi.incident_platform.entity.IncidentComment;
import com.siddhi.incident_platform.entity.Notification;
import com.siddhi.incident_platform.enums.AuditAction;
import com.siddhi.incident_platform.mapper.IncidentMapper;
import com.siddhi.incident_platform.mapper.IncidentCommentMapper;
import com.siddhi.incident_platform.mapper.AuditLogMapper;
import com.siddhi.incident_platform.exception.InvalidStatusTransitionException;
import com.siddhi.incident_platform.exception.ResourceNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final IncidentAuditLogRepository incidentAuditLogRepository;
    private final IncidentCommentRepository incidentCommentRepository;
    private final NotificationRepository notificationRepository;


    public IncidentServiceImpl(IncidentRepository incidentRepository, UserRepository userRepository, IncidentAuditLogRepository incidentAuditLogRepository, IncidentCommentRepository incidentCommentRepository, NotificationRepository notificationRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
        this.incidentAuditLogRepository = incidentAuditLogRepository;
        this.incidentCommentRepository = incidentCommentRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public IncidentResponse createIncident(CreateIncidentRequest request) {
        User createdBy = userRepository.findById(request.getCreatedByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + request.getCreatedByUserId()));

        Incident incident = Incident.builder()
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

        Incident savedIncident = incidentRepository.save(incident);

        return IncidentMapper.toIncidentResponse(savedIncident);
    }

    @Override
    public IncidentResponse getIncidentById(Long incidentId) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id:" + incidentId));

        return IncidentMapper.toIncidentResponse(incident);
    }

    @Override
    public List<IncidentResponse> getAllIncidents() {
        List<Incident> incidents = incidentRepository.findAll();

        return incidents.stream()
                .map(IncidentMapper::toIncidentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncidentResponse> getIncidentByStatus(IncidentStatus incidentStatus) {

        return incidentRepository.findByIncidentStatus(incidentStatus)
                .stream()
                .map(IncidentMapper::toIncidentResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> getIncidentBySeverity(Severity severity){
        return incidentRepository.findBySeverity(severity)
                .stream()
                .map(IncidentMapper::toIncidentResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> getIncidentsByAssignedUserId(Long userId){
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id:" +userId));

        return incidentRepository.findByAssignedTo(user)
                .stream()
                .map(IncidentMapper::toIncidentResponse)
                .toList();
    }

    @Override
    public IncidentResponse updateIncidentStatus(Long incidentId, UpdateIncidentStatusRequest request)
    {
        Incident incident=incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with Id" + incidentId));

        User updatedBy=userRepository.findById(request.getUpdatedByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this id" + request.getUpdatedByUserId()));

        if(request.getIncidentStatus()==null){
            throw new RuntimeException("Incident status cannot be null");
        }

        IncidentStatus currentStatus=incident.getIncidentStatus();
        IncidentStatus newStatus=request.getIncidentStatus();

        if(!isValidStatusTransition(currentStatus,newStatus)) {
            throw new InvalidStatusTransitionException("Invalid status transition from" + currentStatus + "to" + newStatus);
        }


        incident.setIncidentStatus(newStatus);
        incident.setUpdatedAt(LocalDateTime.now());

        if(newStatus==IncidentStatus.RESOLVED) {
            incident.setResolvedAt(LocalDateTime.now());
        }

        if(newStatus==IncidentStatus.CLOSED) {
            incident.setClosedAt(LocalDateTime.now());
        }

        Incident updateIncident=incidentRepository.save(incident);

        IncidentAuditLog incidentAuditLog=IncidentAuditLog.builder()
                .incident(updateIncident)
                .action(AuditAction.STATUS_CHANGED)
                .oldValue(currentStatus.name())
                .newValue(newStatus.name())
                .performedBy(updatedBy)
                .performedAt(LocalDateTime.now())
                .build();

        incidentAuditLogRepository.save(incidentAuditLog);

        return IncidentMapper.toIncidentResponse(updateIncident);
    }

    private boolean isValidStatusTransition(IncidentStatus currentStatus, IncidentStatus newStatus)
    {
        if(currentStatus==IncidentStatus.OPEN && newStatus==IncidentStatus.IN_PROGRESS)
        {
            return true;
        }
        if(currentStatus==IncidentStatus.IN_PROGRESS && newStatus==IncidentStatus.ON_HOLD)
        {
            return true;
        }
        if(currentStatus==IncidentStatus.IN_PROGRESS && newStatus==IncidentStatus.RESOLVED)
        {
            return true;
        }
        if(currentStatus==IncidentStatus.ON_HOLD && newStatus==IncidentStatus.IN_PROGRESS)
        {
            return true;
        }
        if(currentStatus==IncidentStatus.RESOLVED && newStatus==IncidentStatus.CLOSED)
        {
            return true;
        }

        return false;
    }

    @Override
    public IncidentResponse assignIncident(Long incidentId, AssignIncidentRequest request)
    {
        Incident incident=incidentRepository.findById(incidentId)
                .orElseThrow(()-> new ResourceNotFoundException("Incident not found with id:" + incidentId));

        User assignedTo=userRepository.findById(request.getAssignedToUserId())
                .orElseThrow(()-> new ResourceNotFoundException("Assigned user not found with id:" + request.getAssignedToUserId()));

        User assignedBy=userRepository.findById(request.getAssignedByUserId())
                .orElseThrow(()-> new ResourceNotFoundException("Assigning user not found with id" + request.getAssignedByUserId()));

        User previousAssignee = incident.getAssignedTo();

        incident.setAssignedTo(assignedTo);
        incident.setUpdatedAt(LocalDateTime.now());

        Incident updatedIncident=incidentRepository.save(incident);

        IncidentAuditLog incidentAuditLog=IncidentAuditLog.builder()
                .incident(updatedIncident)
                .action(AuditAction.INCIDENT_ASSIGNED)
                .oldValue(previousAssignee!=null?previousAssignee.getEmail():"UNASSIGNED")
                .newValue(assignedTo.getEmail())
                .performedBy(assignedBy)
                .performedAt(LocalDateTime.now())
                .build();

        incidentAuditLogRepository.save(incidentAuditLog);

        Notification notification=Notification.builder()
                .notificationType(NotificationType.INCIDENT_ASSIGNED)
                .message("Incident" + updatedIncident.getTitle() + "has been assigned to you")
                .incident(updatedIncident)
                .recipient(assignedTo)
                .readStatus(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        return IncidentMapper.toIncidentResponse(updatedIncident);
    }

    @Override
    public IncidentCommentResponse addComment(Long incidentId, AddIncidentCommentRequest request ) {

        Incident incident=incidentRepository.findById(incidentId)
                .orElseThrow(()-> new ResourceNotFoundException("Incident not found with id:" + incidentId));

        User commentedBy=userRepository.findById(request.getCommentedByUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id:" + request.getCommentedByUserId()));

        IncidentComment comment= IncidentComment.builder()
                .incident(incident)
                .comment(request.getComment())
                .commentedBy(commentedBy)
                .commentedAt(LocalDateTime.now())
                .build();

        IncidentComment savedComment=incidentCommentRepository.save(comment);

        IncidentAuditLog auditLog=IncidentAuditLog.builder()
                .incident(incident)
                .action(AuditAction.COMMENT_ADDED)
                .oldValue(null)
                .newValue(request.getComment())
                .performedBy(commentedBy)
                .performedAt(LocalDateTime.now())
                .build();

        incidentAuditLogRepository.save(auditLog);

        return IncidentCommentMapper.toIncidentCommentResponse(savedComment);
    }

    @Override
    public List<IncidentCommentResponse> getCommentsByIncident(Long incidentId){

        Incident incident=incidentRepository.findById(incidentId)
                .orElseThrow(()-> new ResourceNotFoundException("Incident not found with id:" + incidentId));

        return incidentCommentRepository.findByIncidentOrderByCommentedAtDesc(incident)
                .stream()
                .map(IncidentCommentMapper::toIncidentCommentResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponse> getAuditLogsByIncident(Long incidentId){

        Incident incident=incidentRepository.findById(incidentId)
                .orElseThrow(()-> new ResourceNotFoundException("Incident not found with id:" + incidentId));

        return incidentAuditLogRepository.findByIncidentOrderByPerformedAtDesc(incident)
                .stream()
                .map(AuditLogMapper::toAuditLogResponse)
                .toList();
    }

    @Override
    public Page<IncidentResponse> getIncidentsWithPagination(int page, int size){

        Pageable pageable=PageRequest.of(page,size);

        return incidentRepository.findAll(pageable)
                .map(IncidentMapper::toIncidentResponse);
    }
}
