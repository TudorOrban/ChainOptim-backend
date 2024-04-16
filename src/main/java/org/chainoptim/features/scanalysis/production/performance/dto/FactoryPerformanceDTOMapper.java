package org.chainoptim.features.scanalysis.production.performance.dto;

import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformance;

public class FactoryPerformanceDTOMapper {

    private FactoryPerformanceDTOMapper() {}

    public static FactoryPerformance mapCreateFactoryPerformanceDTOToFactoryPerformance(CreateFactoryPerformanceDTO dto) {
        FactoryPerformance factoryPerformance = new FactoryPerformance();
        factoryPerformance.setFactoryId(dto.getFactoryId());
        factoryPerformance.setReport(dto.getReport());

        return factoryPerformance;
    }

    public static void setUpdateFactoryPerformanceDTOToFactoryPerformance(UpdateFactoryPerformanceDTO dto, FactoryPerformance factoryPerformance) {
        factoryPerformance.setFactoryId(dto.getFactoryId());
        factoryPerformance.setReport(dto.getReport());
    }
}
