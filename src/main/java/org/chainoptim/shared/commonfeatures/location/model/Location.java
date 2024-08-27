package org.chainoptim.shared.commonfeatures.location.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chainoptim.features.demand.model.Client;
import org.chainoptim.features.demand.model.ClientShipment;
import org.chainoptim.features.production.model.Factory;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.model.SupplierShipment;
import org.chainoptim.features.storage.model.Warehouse;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Supplier> suppliers;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Client> clients;

    @OneToMany(mappedBy = "sourceLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<SupplierShipment> outgoingSupplierShipments;

    @OneToMany(mappedBy = "destinationLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<SupplierShipment> incomingSupplierShipments;

    @OneToMany(mappedBy = "sourceLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ClientShipment> outgoingClientShipments;

    @OneToMany(mappedBy = "destinationLocation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ClientShipment> incomingClientShipments;
}
