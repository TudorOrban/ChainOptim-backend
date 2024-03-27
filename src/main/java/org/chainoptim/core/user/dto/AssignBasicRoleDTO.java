package org.chainoptim.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.core.user.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignBasicRoleDTO {
    private User.Role role;
}
