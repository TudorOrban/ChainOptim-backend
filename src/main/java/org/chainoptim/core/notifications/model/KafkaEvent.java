package org.chainoptim.core.notifications.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEvent<T> {

    private T oldEntity;
    private T newEntity;
    private String entityType;
    private EventType eventType;

    public enum EventType {
        CREATE, UPDATE, DELETE
    }
}
