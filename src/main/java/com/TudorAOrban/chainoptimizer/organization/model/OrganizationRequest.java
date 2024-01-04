package com.TudorAOrban.chainoptimizer.organization.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization_requests")
public class OrganizationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "requester_id", nullable = false)
    private String requestId;

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }

    @Column(nullable = false)
    private RequestStatus status;

    @Column(name = "created_at", nullable = false)
    private java.time.LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
