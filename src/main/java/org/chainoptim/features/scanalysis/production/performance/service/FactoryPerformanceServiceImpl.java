package org.chainoptim.features.scanalysis.production.performance.service;

import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformanceReport;
import org.chainoptim.features.scanalysis.production.performance.model.FactoryStagePerformanceReport;
import org.chainoptim.features.scanalysis.production.productionhistory.model.DailyProductionRecord;
import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;
import org.chainoptim.features.scanalysis.production.productionhistory.model.ProductionHistory;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocation;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FactoryPerformanceServiceImpl implements FactoryPerformanceService {

    public FactoryPerformanceReport computeFactoryPerformanceReport(FactoryProductionHistory productionHistory) {
        FactoryPerformanceReport factoryPerformanceReport = new FactoryPerformanceReport();

        Map<Integer, Map<Float, DailyProductionRecord>> recordsByStageId = groupRecordsByFactoryStageId(productionHistory.getProductionHistory());

        for (Map.Entry<Integer, Map<Float, DailyProductionRecord>> entry : recordsByStageId.entrySet()) {
            Integer factoryStageId = entry.getKey();
            Map<Float, DailyProductionRecord> records = entry.getValue();

            FactoryStagePerformanceReport stagePerformanceReport = computeFactoryStagePerformanceReport(factoryStageId, records);
            factoryPerformanceReport.getStageReports().put(factoryStageId, stagePerformanceReport);
        }


        return factoryPerformanceReport;
    }

    private FactoryStagePerformanceReport computeFactoryStagePerformanceReport(Integer factoryStageId, Map<Float, DailyProductionRecord> productionRecords) {
        FactoryStagePerformanceReport factoryStagePerformanceReport = new FactoryStagePerformanceReport();
        factoryStagePerformanceReport.setFactoryStageId(factoryStageId);

        return factoryStagePerformanceReport;
    }


    private Map<Integer, Map<Float, DailyProductionRecord>> groupRecordsByFactoryStageId(ProductionHistory productionHistory) {
        Map<Integer, Map<Float, DailyProductionRecord>> recordsByStageId = new HashMap<>();

        // Get all factory stage IDs
        for (Map.Entry<Float, DailyProductionRecord> entry : productionHistory.getDailyProductionRecords().entrySet()) {
            DailyProductionRecord dailyRecord = entry.getValue();

            for (ResourceAllocation allocation : dailyRecord.getAllocations()) {
                Integer stageId = allocation.getFactoryStageId();

                recordsByStageId.computeIfAbsent(stageId, k -> new HashMap<>());
            }
        }

        // Distribute record allocations and results to each factory stage
        for (Map.Entry<Integer, Map<Float, DailyProductionRecord>> stageEntry : recordsByStageId.entrySet()) {
            Integer factoryStageId = stageEntry.getKey();

            Map<Float, DailyProductionRecord> stageRecords = new HashMap<>();

            for (Map.Entry<Float, DailyProductionRecord> entry : productionHistory.getDailyProductionRecords().entrySet()) {
                Float daysSinceStart = entry.getKey();
                DailyProductionRecord dailyRecord = entry.getValue();

                DailyProductionRecord restrictedRecord = new DailyProductionRecord();
                restrictedRecord.setDurationDays(dailyRecord.getDurationDays());
                restrictedRecord.setAllocations(dailyRecord.getAllocations().stream()
                        .filter(allocation -> allocation.getFactoryStageId().equals(factoryStageId))
                        .toList());
                restrictedRecord.setResults(dailyRecord.getResults().stream()
                        .filter(result -> result.getFactoryStageId().equals(factoryStageId))
                        .toList());

                stageRecords.put(daysSinceStart, restrictedRecord);
            }

            recordsByStageId.put(factoryStageId, stageRecords);
        }

        return recordsByStageId;
    }
}
