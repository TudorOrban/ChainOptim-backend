package org.chainoptim.core.overview.notifications.dto;

import org.chainoptim.shared.enums.Feature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNotificationDTO {

    private Integer id;
    private String userId;
    private String title;
    private Integer entityId;
    private Feature entityType;
    private String message;
    private Boolean readStatus;
    private String type;
}
