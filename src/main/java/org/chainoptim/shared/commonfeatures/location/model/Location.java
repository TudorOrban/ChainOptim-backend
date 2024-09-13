package org.chainoptim.shared.commonfeatures.location.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chainoptim.features.demand.client.model.Client;
import org.chainoptim.features.demand.clientshipment.model.ClientShipment;
import org.chainoptim.features.production.factory.model.Factory;
import org.chainoptim.features.supply.supplier.model.Supplier;
import org.chainoptim.features.supply.suppliershipment.model.SupplierShipment;
import org.chainoptim.features.storage.warehouse.model.Warehouse;

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

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "zip_code")
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
