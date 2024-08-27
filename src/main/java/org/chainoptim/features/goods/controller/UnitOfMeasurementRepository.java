package org.chainoptim.features.goods.controller;

import org.chainoptim.features.goods.controller.UnitOfMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UnitOfMeasurementRepository extends JpaRepository<UnitOfMeasurement, Integer> {
    
    @Query("SELECT um.organizationId FROM UnitOfMeasurement um WHERE um.id = :unitId")
    Optional<Integer> findOrganizationIdById(@Param("unitId") Long unitId);

    @Query("SELECT um FROM UnitOfMeasurement um WHERE um.organizationId = :organizationId")
    List<UnitOfMeasurement> findUnitsOfMeasurementByOrganizationId(Integer organizationId);

}
