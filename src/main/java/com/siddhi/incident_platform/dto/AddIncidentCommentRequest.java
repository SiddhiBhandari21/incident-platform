package com.siddhi.incident_platform.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddIncidentCommentRequest {

    @NotBlank(message = "Comment is required")
    @Size(max = 2000, message = "Comment cannot exceed 2000 characters")
    private String comment;

    @NotNull(message = "Comment by user ID is required")
    private Long commentedByUserId;

}
