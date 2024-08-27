package org.chainoptim.features.storage.crate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crates")
public class Crate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "component_id", nullable = false)
    private Integer componentId;

    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @Column(name = "volume_m3", nullable = false)
    private Float volumeM3;

    @Column(name = "stackable", nullable = false)
    private Boolean stackable;

    @Column(name = "height_m", nullable = false)
    private Float heightM;
}
