package org.chainoptim.core.notifications.dto;

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
    private String entityType;
    private String message;
    private Boolean readStatus;
    private String type;
}
