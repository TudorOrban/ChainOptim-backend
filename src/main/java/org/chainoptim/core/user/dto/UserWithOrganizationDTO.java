package org.chainoptim.core.user.dto;

import lombok.Data;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.user.model.User;

import java.time.LocalDateTime;

@Data
public class UserWithOrganizationDTO {
    private String id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Organization organization;
    private User.Role role;
}
