package org.chainoptim.core.user.dto;

import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
    private User.Role role;
    private CustomRole customRole;
}

