package org.chainoptim.shared.commonfeatures.location.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

}
