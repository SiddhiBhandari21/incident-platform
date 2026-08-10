package com.siddhi.incident_platform.dto;

import com.siddhi.incident_platform.enums.Severity;
import com.siddhi.incident_platform.enums.SourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateIncidentRequest {

    @NotBlank(message="Title is required")
    @Size(max=255, message="Title cannot exceed 255 characters")
    private String title;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Severity is required")
    private Severity severity;

    @NotNull(message = "SourceType is required")
    private SourceType sourceType;

    @Size(max = 255, message = "External ID cannot exceed 255 characters")
    private String externalId;

    @Size(max = 255, message = "Affected asset cannot exceed 255 characters")
    private String affectedAsset;

    @Size(max = 1000, message = "Detection link cannot exceed 1000 characters")
    private String detectionLink;

    @NotNull(message = "Created by user ID is required")
    private Long createdByUserId;
}
