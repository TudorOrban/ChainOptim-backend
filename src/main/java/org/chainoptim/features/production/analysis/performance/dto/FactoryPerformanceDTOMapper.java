package org.chainoptim.features.production.analysis.performance.dto;

import org.chainoptim.features.production.analysis.performance.model.FactoryPerformance;

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
