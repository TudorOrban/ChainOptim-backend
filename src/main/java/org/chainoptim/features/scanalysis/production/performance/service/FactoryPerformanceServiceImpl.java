package org.chainoptim.features.scanalysis.production.performance.service;

import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.factorygraph.model.StageNode;
import org.chainoptim.features.scanalysis.production.performance.model.FactoryPerformanceReport;
import org.chainoptim.features.scanalysis.production.performance.model.FactoryStagePerformanceReport;
import org.chainoptim.features.scanalysis.production.productionhistory.model.DailyProductionRecord;
import org.chainoptim.features.scanalysis.production.productionhistory.model.FactoryProductionHistory;
import org.chainoptim.features.scanalysis.production.productionhistory.model.ProductionHistory;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.AllocationResult;
import org.chainoptim.features.scanalysis.production.resourceallocation.model.ResourceAllocation;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FactoryPerformanceServiceImpl implements FactoryPerformanceService {

    public FactoryPerformanceReport computeFactoryPerformanceReport(FactoryProductionHistory productionHistory, FactoryGraph factoryGraph) {
        FactoryPerformanceReport factoryPerformanceReport = new FactoryPerformanceReport();

        Map<Integer, Map<Float, DailyProductionRecord>> recordsByStageId = groupRecordsByFactoryStageId(productionHistory.getProductionHistory());

        for (Map.Entry<Integer, Map<Float, DailyProductionRecord>> entry : recordsByStageId.entrySet()) {
            Integer factoryStageId = entry.getKey();
            Map<Float, DailyProductionRecord> records = entry.getValue();

            FactoryStagePerformanceReport stagePerformanceReport = computeFactoryStagePerformanceReport(factoryStageId, records, factoryGraph, productionHistory.getProductionHistory().getStartDate());
            factoryPerformanceReport.getStageReports().put(factoryStageId, stagePerformanceReport);
        }


        return factoryPerformanceReport;
    }

    private FactoryStagePerformanceReport computeFactoryStagePerformanceReport(Integer factoryStageId, Map<Float, DailyProductionRecord> productionRecords, FactoryGraph factoryGraph, LocalDateTime startDate) {
        FactoryStagePerformanceReport factoryStagePerformanceReport = new FactoryStagePerformanceReport();
        factoryStagePerformanceReport.setFactoryStageId(factoryStageId);

        float overallScore = 0;
        float resourceReadinessScore = 0;
        float resourceUtilizationScore = 0;

        float errorRate = 0;

        float totalExecutedStages = 0f;
        float totalTimeDays = 0f;
        float averageExecutedCapacityPerDay = 0f;
        float minimumExecutedCapacityPerDay = Float.MAX_VALUE;
        float daysUnderCapacityPercentage = 0f;

        for (Map.Entry<Float, DailyProductionRecord> entry : productionRecords.entrySet()) {
            DailyProductionRecord dailyRecord = entry.getValue();
            StageNode stageNode = factoryGraph.getNodes().get(factoryStageId);
            float stageCapacity = stageNode.getNumberOfStepsCapacity() * dailyRecord.getDurationDays();

            float executedStages = determineStageExecutedStages(dailyRecord.getResults(), factoryGraph);
            totalExecutedStages += executedStages;

            if (executedStages < stageCapacity) {
                daysUnderCapacityPercentage++;
            }

            totalTimeDays += dailyRecord.getDurationDays();

            float executedCapacityPerDay = executedStages / dailyRecord.getDurationDays();
            averageExecutedCapacityPerDay += executedCapacityPerDay;

            if (executedCapacityPerDay < minimumExecutedCapacityPerDay) {
                minimumExecutedCapacityPerDay = executedCapacityPerDay;
            }
        }

        float totalDuration = determineRecordsDuration(productionRecords);
        averageExecutedCapacityPerDay /= totalDuration;
        daysUnderCapacityPercentage /= totalDuration * 100;

        factoryStagePerformanceReport.setOverallScore(overallScore);
        factoryStagePerformanceReport.setResourceReadinessScore(resourceReadinessScore);
        factoryStagePerformanceReport.setResourceUtilizationScore(resourceUtilizationScore);

        factoryStagePerformanceReport.setErrorRate(errorRate);

        factoryStagePerformanceReport.setTotalExecutedStages(totalExecutedStages);
        factoryStagePerformanceReport.setTotalTimeDays(totalTimeDays);
        factoryStagePerformanceReport.setAverageExecutedCapacityPerDay(averageExecutedCapacityPerDay);
        factoryStagePerformanceReport.setMinimumExecutedCapacityPerDay(minimumExecutedCapacityPerDay);
        factoryStagePerformanceReport.setDaysUnderCapacityPercentage(daysUnderCapacityPercentage);

        return factoryStagePerformanceReport;
    }


    private float determineStageExecutedStages(List<AllocationResult> stageResults, FactoryGraph factoryGraph) {
        List<Float> allocatedRequestedRations = stageResults.stream()
                .map(result -> result.getActualAmount() / result.getFullAmount())
                .toList();

        return allocatedRequestedRations.stream().min(Float::compare).orElse(0.0f);
    }

    private float determineRecordsDuration(Map<Float, DailyProductionRecord> productionRecords) {
        return productionRecords.values().stream()
                .map(DailyProductionRecord::getDurationDays)
                .reduce(0f, Float::sum);
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
