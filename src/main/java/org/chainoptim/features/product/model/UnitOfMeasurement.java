package org.chainoptim.features.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.chainoptim.features.productpipeline.model.Component;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "units_of_measurement")
public class UnitOfMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "organization_id")
    private Integer organizationId;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Component> components;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;
}
