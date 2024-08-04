package org.chainoptim.core.upcomingevents.model;

import org.chainoptim.shared.enums.Feature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "upcoming_events")
public class UpcomingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "associated_entity_id")
    private Integer associatedEntityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "associated_entity_type")
    private Feature associatedEntityType;

}
