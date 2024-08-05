package org.chainoptim.shared.commonfeatures.location.repository;

import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("SELECT l.organizationId FROM Location l WHERE l.id = :locationId")
    Optional<Integer> findOrganizationIdById(@Param("locationId") Long locationId);

    @Query("SELECT l FROM Location l WHERE l.organizationId = :organizationId")
    List<Location> findLocationsByOrganizationId(Integer organizationId);

    @Query("SELECT l FROM Location l WHERE l.id IN :locationIds")
    List<Location> findByLocationIds(List<Integer> locationIds);
}
