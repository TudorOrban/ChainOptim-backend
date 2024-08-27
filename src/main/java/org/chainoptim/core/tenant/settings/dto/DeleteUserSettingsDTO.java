package org.chainoptim.core.tenant.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserSettingsDTO {

    private Integer id;
    private String userId;
}
