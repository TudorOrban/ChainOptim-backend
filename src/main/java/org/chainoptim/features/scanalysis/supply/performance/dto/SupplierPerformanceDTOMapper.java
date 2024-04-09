package org.chainoptim.features.scanalysis.supply.performance.dto;

import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformance;

public class SupplierPerformanceDTOMapper {

    private SupplierPerformanceDTOMapper() {}

    public static SupplierPerformance mapCreateSupplierPerformanceDTOToSupplierPerformance(CreateSupplierPerformanceDTO dto) {
        SupplierPerformance supplierPerformance = new SupplierPerformance();
        supplierPerformance.setSupplierId(dto.getSupplierId());
        supplierPerformance.setReport(dto.getReport());

        return supplierPerformance;
    }

    public static void setUpdateSupplierPerformanceDTOToSupplierPerformance(UpdateSupplierPerformanceDTO dto, SupplierPerformance supplierPerformance) {
        supplierPerformance.setSupplierId(dto.getSupplierId());
        supplierPerformance.setReport(dto.getReport());
    }
}
