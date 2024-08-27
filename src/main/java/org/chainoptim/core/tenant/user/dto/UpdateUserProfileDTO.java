package org.chainoptim.core.tenant.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileDTO {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String imageUrl;
    private Boolean isProfilePublic;
}
