package org.chainoptim.core.tenant.organization.dto;

import org.chainoptim.core.tenant.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrganizationUserDTO {
    String username;
    String email;
    User.Role role;
}
