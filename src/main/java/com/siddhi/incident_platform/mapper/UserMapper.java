package com.siddhi.incident_platform.mapper;

import com.siddhi.incident_platform.dto.UserResponse;
import com.siddhi.incident_platform.entity.User;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.getActive())
                .build();

    }

}
