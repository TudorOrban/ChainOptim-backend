package org.chainoptim.core.organization.dto;

import org.chainoptim.core.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrganizationUserDTO {
    String username;
    String password;
    String email;
    Integer organizationId;
    User.Role role;
}
