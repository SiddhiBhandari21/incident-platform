package com.siddhi.incident_platform.dto;

import com.siddhi.incident_platform.enums.Severity;
import com.siddhi.incident_platform.enums.SourceType;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateIncidentRequest {

    private String title;

    private String description;

    private Severity severity;

    private SourceType sourceType;

    private String externalId;

    private String affectedAsset;

    private String detectionLink;

    private Long createdByUserId;
}
