package org.chainoptim.shared.commonfeatures.location.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.chainoptim.features.supply.model.SupplierShipment;
import org.chainoptim.features.warehouse.model.Warehouse;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "state", nullable = true)
    private String state;

    @Column(name = "country", nullable = true)
    private String country;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    @Column(name = "zip_code", nullable = true)
    private String zipCode;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Factory> factories;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Warehouse> warehouses;

    @OneToMany(mappedBy = "sourceLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<SupplierShipment> outgoingShipments;

    @OneToMany(mappedBy = "destinationLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<SupplierShipment> incomingShipments;
}