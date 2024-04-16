package org.chainoptim.features.scanalysis.production.performance.dto;

import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformanceReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFactoryPerformanceDTO {

    private Integer factoryId;
    private FactoryPerformanceReport report;
}
