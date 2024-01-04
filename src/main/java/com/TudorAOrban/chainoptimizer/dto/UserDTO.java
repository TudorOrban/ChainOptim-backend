package com.TudorAOrban.chainoptimizer.dto;

import com.TudorAOrban.chainoptimizer.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String username;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
    private User.Role role;
}

