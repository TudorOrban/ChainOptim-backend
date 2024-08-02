package org.chainoptim.features.factory.model;

import org.chainoptim.shared.commonfeatures.location.model.Location;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "factories")
public class Factory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "overall_score")
    private Float overallScore;

    @Column(name = "resource_distribution_score")
    private Float resourceDistributionScore;

    @Column(name = "resource_readiness_score")
    private Float resourceReadinessScore;

    @Column(name = "resource_utilization_score")
    private Float resourceUtilizationScore;

    @OneToMany(mappedBy = "factory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FactoryStage> factoryStages;
}
