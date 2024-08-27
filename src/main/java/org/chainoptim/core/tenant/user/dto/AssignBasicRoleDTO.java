package org.chainoptim.core.tenant.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.core.tenant.user.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignBasicRoleDTO {
    private User.Role role;
}
