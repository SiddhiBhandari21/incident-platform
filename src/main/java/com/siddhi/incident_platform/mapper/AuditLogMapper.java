package com.siddhi.incident_platform.mapper;

import com.siddhi.incident_platform.dto.AuditLogResponse;
import com.siddhi.incident_platform.entity.IncidentAuditLog;
import org.apache.catalina.mapper.Mapper;


public class AuditLogMapper {

    public static AuditLogResponse toAuditLogResponse(IncidentAuditLog incidentAuditLog)
    {
        if(incidentAuditLog == null){
            return null;
        }

        return AuditLogResponse.builder()
                .Id(incidentAuditLog.getId())
                .action(incidentAuditLog.getAction())
                .oldValue(incidentAuditLog.getOldValue())
                .newValue(incidentAuditLog.getNewValue())
                .performedBy(UserMapper.toUserResponse(incidentAuditLog.getPerformedBy()))
                .performedAt(incidentAuditLog.getPerformedAt())
                .build();
    }


}
