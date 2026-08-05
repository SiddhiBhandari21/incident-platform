package com.siddhi.incident_platform.mapper;

import com.siddhi.incident_platform.dto.IncidentCommentResponse;
import com.siddhi.incident_platform.entity.IncidentComment;

public class IncidentCommentMapper {

    public static IncidentCommentResponse toIncidentCommentResponse(IncidentComment incidentComment) {
        if (incidentComment == null){
            return null;
        }

        return IncidentCommentResponse.builder()
                .Id(incidentComment.getId())
                .comment(incidentComment.getComment())
                .commentedBy(UserMapper.toUserResponse(incidentComment.getCommentedBy()))
                .commentedAt(incidentComment.getCommentedAt())
                .build();
    }
}
