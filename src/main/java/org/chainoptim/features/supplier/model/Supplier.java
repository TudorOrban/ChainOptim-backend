package org.chainoptim.features.supplier.model;

import jakarta.persistence.*;
import lombok.*;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "suppliers")
public class Supplier {

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

    @JoinColumn(name = "organization_id", nullable = false)
    private Integer organizationId;

    // Performance
    @Column(name = "overall_score")
    private Float overallScore;

    @Column(name = "timeliness_score")
    private Float timelinessScore;

    @Column(name = "quantity_per_time_score")
    private Float quantityPerTimeScore;

    @Column(name = "availability_score")
    private Float availabilityScore;

    @Column(name = "quality_score")
    private Float qualityScore;
}
