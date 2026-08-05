package com.siddhi.incident_platform.dto;

import com.siddhi.incident_platform.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private UserRole role;

    private Boolean active;
}
